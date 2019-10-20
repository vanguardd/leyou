package com.leyou.item.web;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Title: 商品分类接口
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/10/04
 */
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据商品分类父id查询商品分类集合
     * @param pid
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Category>>
     * @author vanguard
     * @date 19/10/15 20:41
     */
    @GetMapping("list")
    public ResponseEntity<List<Category>> queryByParentId(@RequestParam("pid") Long pid) {
        List<Category> categoryList = categoryService.queryListByParent(pid);
        return ResponseEntity.ok(categoryList);
    }
}
