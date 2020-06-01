package com.leyou.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.PageResult;
import com.leyou.dao.BrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BrandService {
    @Autowired
    BrandMapper brandMapper;
    public PageResult<Brand> findBrand(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //pageHelper
        PageHelper.startPage(page,rows);
        List<Brand> brandList= brandMapper.findBrand(key,sortBy,desc);
        PageInfo<Brand> pageInfo=new PageInfo<Brand>(brandList);
        return new PageResult<Brand>(pageInfo.getTotal(),pageInfo.getList());
    }
    public PageResult<Brand> findBrandByLimit(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //pageHelper
        //PageHelper.startPage(page,rows);
        List<Brand> brandList= brandMapper.findBrandLimit(key,(page-1)*rows,rows,sortBy,desc);
        //PageInfo<Brand> pageInfo=new PageInfo<Brand>(brandList);
         Long brandCount=brandMapper.findBrandCount(key,sortBy,desc);
        return new PageResult<Brand>(brandCount,brandList);
    }
    public  void brandCategorySave(Brand brand,List<String> cids){
        brandMapper.insert(brand);
        cids.forEach(id ->{
            brandMapper.addBrandAndCategory(brand.getId(),Long.parseLong(id));
        });
    }

    public void deleteById(Long id) {
        Brand brand=new Brand();
        brand.setId(id);
        brandMapper.deleteByPrimaryKey(brand);
        brandMapper.deleteBrandandCategory(id);
    }

    public List<Category> findCategoryByBrandId(Long pid) {
        return  brandMapper.findCategoryByBrandId(pid);
    }

    public void updateBrand(Brand brand, List<String> cids) {
        brandMapper.updateByPrimaryKey(brand);
        brandMapper.deleteBrandandCategory(brand.getId());
        cids.forEach(cid ->{
          brandMapper.addBrandAndCategory(brand.getId(),Long.parseLong(cid));
        });
    }

    public List<Brand> findBrandByCid(Long cid) {
        return brandMapper.findBrandByCid(cid);
    }
}
