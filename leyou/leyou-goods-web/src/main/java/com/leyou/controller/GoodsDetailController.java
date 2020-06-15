package com.leyou.controller;

import com.leyou.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class GoodsDetailController {
    @Autowired
    GoodService goodService;
    @RequestMapping("hello")
    public String hello(Model model){
        String name="张哲彬";
        model.addAttribute("name",name);
        return "hello";
    }
    @RequestMapping("item/{spuId}.html")
    public String item(@PathVariable("spuId")Long spuId, Model model){

/*        //spu
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
        model.addAttribute("groups",groups);*/
        Map<String, Object> map = goodService.item(spuId);
        model.addAllAttributes(map);
        //写入静态文件
        goodService.creatHtml(spuId);
        return "item";

           }

/*    public void creatHtml(Spu spu, SpuDetail spudetail, List<Category> categoryList, Brand brand, List<Sku> skuList, List<SpecGroup> groups, Map<Long, String> paramMap) {
                 PrintWriter writer=null;
        try {
            //1.创建上下文
            Context context=new Context();
            //把数据放入到上下文
            context.setVariable("spu",spu);
            context.setVariable("spudetail",spudetail);
            context.setVariable("categoryList",categoryList);
            context.setVariable("brand",brand);
            context.setVariable("skuList",skuList);
            context.setVariable("groups",groups);
            context.setVariable("paramMap",paramMap);
            //写入文件，写入流
            File file=new File("D:\\peizhi\\nginx-1.16.1\\html\\"+spu.getId()+".html");
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
    }*/
}
