package com.leyou.item.api;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @title: 规格参数API
 * @description:
 * @author: vanguard
 * @version: 1.0
 * @date: 2020/04/16
 */
@RequestMapping("spec")
public interface SpecificationApi {

    /**
     * 根据条件查询规格参数列表集合
     * @param gid 规格参数组id
     * @param cid 分类Id
     * @param generic 是否通用属性
     * @param searching 是否可搜索
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.SpecParam>>
     * @author vanguard
     * @date 19/12/17 21:01
     */
    @GetMapping("/params")
    List<SpecParam> getSpecParamsByGroupId(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching
    );

    /**
     * 根据分类Id查询规格参数组及组内参数集合
     * @param cid
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.SpecGroup>>
     * @author vanguard
     * @date 20/4/22 21:14
     */
    @GetMapping("{cid}")
    List<SpecGroup> getSpecsByCid(@PathVariable("cid") Long cid);
}
