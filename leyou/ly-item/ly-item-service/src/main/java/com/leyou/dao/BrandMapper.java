package com.leyou.dao;

import com.leyou.pojo.Brand;
import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface BrandMapper extends Mapper<Brand> {

    List<Brand> findBrand(@Param("key") String key, @Param("sortBy") String sortBy, @Param("desc") Boolean desc);

    List<Brand> findBrandLimit(@Param("key") String key, @Param("page") int page, @Param("rows") Integer rows, @Param("sortBy") String sortBy, @Param("desc") Boolean desc);

    Long findBrandCount(@Param("key") String key, @Param("sortBy") String sortBy, @Param("desc") Boolean desc);
    @Insert("insert into tb_category_brand values (#{cid},#{bid})")
    void addBrandAndCategory(Long cid, Long bid);
    @Delete("delete from tb_category_brand where brand_id=#{id}")
    void deleteBrandandCategory(Long id);
    @Select("SELECT y.* FROM tb_category_brand t,tb_category Y WHERE t.category_id=y.id AND t.brand_id=#{pid}")
    List<Category> findCategoryByBrandId(Long pid);
    @Select("SELECT d.* FROM tb_brand d,tb_category_brand b WHERE d.id=b.brand_id AND b.category_id=#{cid}")
    List<Brand> findBrandByCid(Long cid);
}
