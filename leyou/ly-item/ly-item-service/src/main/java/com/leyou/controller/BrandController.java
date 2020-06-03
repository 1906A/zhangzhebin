package com.leyou.controller;

import com.leyou.common.PageResult;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import com.leyou.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @RequestMapping("page")
    public Object findBrandPage(@RequestParam("key") String key,
                                 @RequestParam("page") Integer page,
                                @RequestParam("rows")Integer rows,
                                 @RequestParam("sortBy")String sortBy,
                                @RequestParam("desc")Boolean desc){

        System.out.println(key+"==="+page+"==="+rows+"==="+sortBy+"==="+desc);
        PageResult<Brand> brandList=brandService.findBrand(key,page,rows,sortBy,desc);
        //PageResult<Brand> brandList=brandService.findBrandByLimit(key,page,rows,sortBy,desc);
        return brandList;
    }
    @RequestMapping("addOrEditBrand")
    public  void addOrEditBrand( Brand brand,
                                @RequestParam("cids")List<String> cids){
        if(brand.getId()!=null){
            brandService.updateBrand(brand,cids);
        }else {
            brandService.brandCategorySave(brand, cids);
        }
    }
    @RequestMapping("deleteById/{id}")
    public  void deleteById(@PathVariable("id") Long id){
        brandService.deleteById(id);

    }
    //是、根据品牌id查询具体分类
    @RequestMapping("bid/{id}")
    public  List<Category> findCategoryByBrandId(@PathVariable("id") Long pid){
        return brandService.findCategoryByBrandId(pid);

    }
    //根据分类id查询对应的品牌
    @RequestMapping("cid/{cid}")
    public  List<Brand> findBrandByCid(@PathVariable("cid")Long cid){
        return  brandService.findBrandByCid(cid);
    }

     //根据品牌id查询品牌对象
    @RequestMapping("findBrandById")
    public Brand findBrandById(@RequestParam("id")Long id){
        return brandService.findBrandById(id);
    }
}
