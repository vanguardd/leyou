package com.leyou.item.web;

import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        List<Category> categoryList = categoryService.queryListByParentId(pid);
        return ResponseEntity.ok(categoryList);
    }

    /**
     * 根据品牌Id查询品牌所属的分类
     * @param bid 品牌Id
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Category>>
     * @author vanguard
     * @date 19/12/9 20:48
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryByBid(@PathVariable("bid") Long bid) {
        List<Category> categoryList =  categoryService.queryByBrandId(bid);
        return ResponseEntity.ok(categoryList);
    }

    /**
     * 根据商品分类id集合查询商品分类名称集合
     * @param ids
     * @return org.springframework.http.ResponseEntity<java.util.List<java.lang.String>>
     * @author vanguard
     * @date 2020/4/16 16:02
     */
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("id") List<Long> ids) {
        List<String> names = categoryService.queryNamesByIds(ids);
        return ResponseEntity.ok(names);
    }

}
