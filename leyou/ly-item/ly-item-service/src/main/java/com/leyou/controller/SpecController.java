package com.leyou.controller;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecController {
    @Autowired
    private SpecGroupService specGroupService;
    //保存商品规格组
    @RequestMapping("group")
    public void saveSpecGroup(@RequestBody SpecGroup specGroup){
        if(specGroup.getId()==null){
            specGroupService.saveSpecGroup(specGroup);
        }else{
            specGroupService.updateSpecGroup(specGroup);
        }

    }
    //查询规格参数组列表
    @RequestMapping("groups/{cid}")
    public List<SpecGroup> findSpecGroup(@PathVariable("cid")Long cateGroyId){
        return specGroupService.findSpecGroup(cateGroyId);
    }

    @RequestMapping("group/{id}")
    public  void deleteBySpecGroupId(@PathVariable("id")Long id){
        specGroupService.deleteBySpecGroupId(id);
    }


    @RequestMapping("params")
    public List<SpecParam> findSpecParamBygid(@RequestParam("gid")Long gid) {
        return specGroupService.findSpecParamBygid(gid);
    }
}
