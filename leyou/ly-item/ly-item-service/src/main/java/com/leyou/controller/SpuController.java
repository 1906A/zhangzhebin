package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.SpuService;
import com.leyou.vo.SpuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("spu")
public class SpuController {

    @Autowired
    private SpuService spuService;
    @RequestMapping("page")
    public PageResult<SpuVo> findSpuPage(@RequestParam("key") String key,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("rows")Integer rows,
                                         @RequestParam("saleable") Integer saleable){
        return spuService.findSpuPage(key,page,rows,saleable);

    }
    //保存商品信息
     @RequestMapping("saveOrUpdateGoods")
    public  void saveSpuDetail(@RequestBody SpuVo spuVo){
    if(spuVo.getId()!=null){
        spuService.updateSpuDetail(spuVo);
    }else{
        spuService.saveSpuDetail(spuVo);
    }

     }
     //根据spuid查询商品集
     @RequestMapping("detail/{spuId}")
    public SpuDetail findSpuDetailBySpuId(@PathVariable("spuId")Long spuId){
      return spuService.findSpuDetailBySpuId(spuId);
     }
     //根据spuid删除spu详情
     @RequestMapping("deleteById/{spuId}")
    public  void deleteSpuBySpuId(@PathVariable("spuId")Long spuId){
        spuService.deleteSpuBySpuId(spuId);
     }
    @RequestMapping("upOrDown")
    public  void upOrDown(@RequestParam("spuId")Long spuId,@RequestParam("saleable")int saleable){
        spuService.upOrDown(spuId,saleable);
    }
    //根据spuid查询spu
    @RequestMapping("findSpuById")
    public Spu findSpuById(@RequestParam("spuId")Long spuId){
         return  spuService.findSpuById(spuId);
    }
    @RequestMapping("findSpuBySpuId")
    public  SpuVo findSpuBySpuId(@RequestParam("spuId")Long spuId){
       return spuService.findSpuBySpuId(spuId);
    }
}
