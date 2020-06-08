package com.leyou;

import com.leyou.pojo.SpecParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("specParam")
public interface SpecParamClientServer {
    @RequestMapping("paramByCidAndGeneric")
    public List<SpecParam> findparamByCidAndGeneric(@RequestParam("cid")Long cid, @RequestParam("generic")boolean generic);
}
