package com.leyou.service;

import com.leyou.client.*;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodService {
    @Autowired
    SpuClient spuClient;
    @Autowired
    SkuClient skuClient;
    @Autowired
    SpecGroupClient specGroupClient;
    @Autowired
    CategoryClient categoryClient;
    @Autowired
    SpecParamClient specParamClient;
    @Autowired
    BrandClient brandClient;
    @Autowired
    TemplateEngine templateEngine;
    public  Map<String,Object>  item(Long spuId) {
        //spu
        Spu spu = spuClient.findSpuById(spuId);

        //spudetail
        SpuDetail spudetail = spuClient.findSpuDetailBySpuId(spuId);

        //sku
        List<Sku> skuList = skuClient.findSkusBySpuId(spuId);

        //查询品牌
        Brand brand = brandClient.findBrandById(spu.getBrandId());


        //查询规格参数组及组内的信息
        List<SpecGroup> groups = specGroupClient.findSpecGroup(spu.getCid3());

        //三级分类
        List<Category> categoryList = categoryClient.findCategoryByCids(
                Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3())
        );

        //参数中的特殊属性
        List<SpecParam> specParamList = specParamClient.findparamByCidAndGeneric(spu.getCid3(), false);
        //规格参数的特殊属性
        Map<Long, String> paramMap = new HashMap<>();
        specParamList.forEach(param -> {
            paramMap.put(param.getId(), param.getName());
        });
        Map<String, Object> map = new HashMap<>();
        map.put("paramMap", paramMap);

        map.put("spu", spu);
        map.put("spudetail", spudetail);
        map.put("categoryList", categoryList);
        map.put("brand", brand);
        map.put("skuList", skuList);
        map.put("groups", groups);
        return map;
    }
        public void creatHtml(Long spuId) {
            PrintWriter writer=null;
            try {
                //1.创建上下文
                Context context=new Context();
/*                //把数据放入到上下文
                context.setVariable("spu",spu);
                context.setVariable("spudetail",spudetail);
                context.setVariable("categoryList",categoryList);
                context.setVariable("brand",brand);
                context.setVariable("skuList",skuList);
                context.setVariable("groups",groups);
                context.setVariable("paramMap",paramMap);*/
                context.setVariables(this.item(spuId));
                //写入文件，写入流
                File file=new File("D:\\peizhi\\nginx-1.16.1\\html\\"+spuId+".html");
                writer=new PrintWriter(file);
                //执行静态化
                templateEngine.process("item",context,writer);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                if(writer!=null){
                    writer.close();
                }
            }
        }

        //删除静态页面
    public void deleteHtml(Long spuId) {
        File file=new File("D:\\peizhi\\nginx-1.16.1\\html\\"+spuId+".html");
        if(file!=null && file.exists()) {
            file.delete();
        }
    }
}

