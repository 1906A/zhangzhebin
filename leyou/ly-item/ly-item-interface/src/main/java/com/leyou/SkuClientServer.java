package com.leyou;

import com.leyou.pojo.Sku;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@RequestMapping("sku")
public interface SkuClientServer {
    @RequestMapping("list")
    public List<Sku> findSkusBySpuId(@RequestParam("id") Long id);

}
