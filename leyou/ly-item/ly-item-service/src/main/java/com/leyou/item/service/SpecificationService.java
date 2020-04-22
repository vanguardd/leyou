package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Iterator;
import java.util.List;

/**
 * @Title:
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/12/17
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 根据分类Id查询规格参数组
     * @param cid
     * @return java.util.List<com.leyou.item.pojo.SpecGroup>
     * @author vanguard
     * @date 19/12/17 20:50
     */
    public List<SpecGroup> getSpecGroupByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        List<SpecGroup> specGroupList = specGroupMapper.select(specGroup);
        if(CollectionUtils.isEmpty(specGroupList)) {
            throw new LyException(ExceptionEnums.SPEC_GROUP_NOT_FOUND);
        }
        return specGroupList;
    }

    /**
     * 根据条件查询规格参数
     * @param gid 规格参数组id
     * @param cid 分类Id
     * @param generic 是否通用属性
     * @param searching 是否可搜索
     * @return java.util.List<com.leyou.item.pojo.SpecParam>
     * @author vanguard
     * @date 20/1/2 20:44
     */
    public List<SpecParam> getSpecParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setGeneric(generic);
        specParam.setSearching(searching);
        List<SpecParam> specParamList = specParamMapper.select(specParam);
        if(CollectionUtils.isEmpty(specParamList)) {
            throw new LyException(ExceptionEnums.SPEC_PARAM_NOT_FOUND);
        }
        return specParamList;
    }

    public List<SpecGroup> getSpecsByCid(Long cid) {

        List<SpecGroup> specGroups = getSpecGroupByCid(cid);
        if(CollectionUtils.isEmpty(specGroups)) {
            throw new LyException(ExceptionEnums.SPEC_GROUP_NOT_FOUND);
        }
        specGroups.forEach(specGroup -> {
            specGroup.setParams(getSpecParams(specGroup.getId(), null, null, null));
        });
        return specGroups;
    }
}
