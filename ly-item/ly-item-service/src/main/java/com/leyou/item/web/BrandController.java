package com.leyou.item.web;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title: 品牌相关接口
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/10/16
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryPageBrandBySearch(@RequestParam Integer page,
                                                    @RequestParam Integer rows,
                                                    @RequestParam String sortBy,
                                                    @RequestParam Boolean desc,
                                                    @RequestParam String key) {
        PageResult<Brand> pageResult = brandService.queryPageBrandsBySearch(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(pageResult);
    }
}
