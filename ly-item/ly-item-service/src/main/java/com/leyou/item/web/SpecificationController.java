package com.leyou.item.web;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Title: 规格参数相关接口
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/12/17
 */
@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类Id查询规格参数组列表集合
     * @param cid
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.SpecGroup>>
     * @author vanguard
     * @date 19/12/17 20:51
     */
    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> getSpecGroupsByCid(@PathVariable("cid") Long cid) {
        List<SpecGroup> specGroupList = specificationService.getSpecGroupByCid(cid);
        return ResponseEntity.ok(specGroupList);
    }

    /**
     * 根据规格参数组Id查询规格参数列表集合
     * @param gid
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.SpecParam>>
     * @author vanguard
     * @date 19/12/17 21:01
     */
    @GetMapping("/params/{gid}")
    public ResponseEntity<List<SpecParam>> getSpecParamsByGroupId(@PathVariable("gid") Long gid) {
        List<SpecParam> specParamList = specificationService.getSpecParamsByGroupId(gid);
        return ResponseEntity.ok(specParamList);
    }

}
