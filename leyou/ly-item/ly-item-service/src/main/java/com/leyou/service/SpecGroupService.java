package com.leyou.service;

import com.leyou.dao.SpecGroupMapper;
import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
        return  specGroupMapper.select(specGroup);
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
