package com.leyou.item.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @title: 分类API
 * @description:
 * @author: vanguard
 * @version: 1.0
 * @date: 2020/04/16
 */
@RequestMapping("category")
public interface CategoryApi {

    /**
     * 根据商品分类id集合查询商品分类名称集合
     * @param ids
     * @return org.springframework.http.ResponseEntity<java.util.List<java.lang.String>>
     * @author vanguard
     * @date 2020/4/16 16:02
     */
    @GetMapping("names")
    List<String> queryNamesByIds(@RequestParam("id") List<Long> ids);

    /**
     * 根据商品分类id查询商品分类id和商品分类name Map的集合
     * @param ids
     * @return java.util.List<java.util.Map<java.lang.Long,java.lang.Object>>
     * @author vanguard
     * @date 20/4/20 18:38
     */
    @GetMapping("id/names/list")
    List<Map<String, Object>> queryNameMapByIds(@RequestParam("id") List<Long> ids);
}
