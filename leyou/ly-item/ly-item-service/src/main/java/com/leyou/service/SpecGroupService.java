package com.leyou.service;

import com.leyou.dao.SpecGroupMapper;
import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpecGroupService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper  specParamMapper;
    public void saveSpecGroup(@RequestBody SpecGroup specGroup) {
         specGroupMapper.insert(specGroup);
    }

    public List<SpecGroup> findSpecGroup(Long cateGroyId) {
        SpecGroup specGroup=new SpecGroup();
        specGroup.setCid(cateGroyId);

        //根据分类id查询规格参数组及组内的参数列表
        List<SpecGroup> groupList=new ArrayList<>();
        groupList=specGroupMapper.select(specGroup);
        groupList.forEach(group ->{
            SpecParam param=new SpecParam();
            param.setGroupId(group.getId());
           group.setParams(specParamMapper.select(param));
        });
        return groupList;
    }

    public void deleteBySpecGroupId(Long id) {
        specGroupMapper.deleteByPrimaryKey(id);
    }

    public void updateSpecGroup(SpecGroup specGroup) {
        specGroupMapper.updateByPrimaryKey(specGroup);
    }

    public List<SpecParam> findSpecParamBygid(Long gid) {
        SpecParam specParam=new SpecParam();
        specParam.setGroupId(gid);
      return  specParamMapper.select(specParam);
    }
}
