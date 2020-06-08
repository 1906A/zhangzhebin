package com.leyou.controller;

import com.leyou.client.*;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsDetailController {
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
    @RequestMapping("hello")
    public String hello(Model model){
        String name="张哲彬";
        model.addAttribute("name",name);
        return "hello";
    }
    @RequestMapping("item/{spuId}.html")
    public String item(@PathVariable("spuId")Long spuId, Model model){

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
        List<Category> categoryList=categoryClient.findCategoryByCids(
                Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3())
        );

        //参数中的特殊属性
        List<SpecParam> specParamList = specParamClient.findparamByCidAndGeneric(spu.getCid3(), false);
        //规格参数的特殊属性
        Map<Long,String> paramMap = new HashMap<>();
        specParamList.forEach(param->{
            paramMap.put(param.getId(),param.getName());
        });
        model.addAttribute("paramMap",paramMap);

        model.addAttribute("spu",spu);
        model.addAttribute("spudetail",spudetail);
        model.addAttribute("categoryList",categoryList);
        model.addAttribute("brand",brand);
        model.addAttribute("skuList",skuList);
        model.addAttribute("groups",groups);
        return "item";

           }
}
