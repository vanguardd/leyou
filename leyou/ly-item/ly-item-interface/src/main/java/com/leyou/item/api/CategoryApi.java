package com.leyou.item.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
    List<String> queryNamesByIds(@RequestParam("id") List<Long> ids);
}
