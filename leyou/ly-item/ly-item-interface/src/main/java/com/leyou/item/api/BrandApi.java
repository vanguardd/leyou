package com.leyou.item.api;

import com.leyou.item.pojo.Brand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @title: 品牌API
 * @description:
 * @author: vanguard
 * @version: 1.0
 * @date: 2020/04/16
 */
@RequestMapping("brand")
public interface BrandApi {

    /**
     * 根据id查询品牌
     * @param bid
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Brand>
     * @author vanguard
     * @date 19/10/21 21:38
     */
    @GetMapping("bid/{bid}")
    Brand getBrand(@PathVariable("bid") Long bid);
}
