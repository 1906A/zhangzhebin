package com.leyou;

import com.leyou.client.SpuClient;
import com.leyou.common.PageResult;
import com.leyou.item.Goods;
import com.leyou.repository.GoodsRepository;
import com.leyou.service.GoodsService;
import com.leyou.vo.SpuVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LySearchApplicationTests {

    @Autowired
    private SpuClient spuClient;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    GoodsRepository goodsRepository;
    @Test
    public void contextLoads() {
        elasticsearchTemplate.createIndex(Goods.class);
        elasticsearchTemplate.putMapping(Goods.class);
        PageResult<SpuVo> pageResult = spuClient.findSpuPage("",1,200,2);
        pageResult.getItems().forEach(spuVo ->{
            System.out.println(spuVo.getId());
            try {
                Goods goods = goodsService.convert(spuVo);
                goodsRepository.save(goods);
            }catch (Exception e){
                e.printStackTrace();
            }

        });



    }

}
