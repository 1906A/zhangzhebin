package com.leyou;

import com.leyou.pojo.SpecParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("specParam")
public interface SpecClientServer {
    @RequestMapping("paramByCid")
    public List<SpecParam> findSpecParamByCidAndSearch(@RequestParam("cid")Long cid);


}
