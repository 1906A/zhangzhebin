package com.leyou.dao;

import com.leyou.pojo.Sku;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface SkuMapper extends Mapper<Sku> {
    @Select("SELECT s.*,k.stock FROM tb_sku s,tb_stock k WHERE s.id=k.sku_id AND spu_id=#{id} and enable=1")
    List<Sku> findSkusBySpuId(Long id);
}
