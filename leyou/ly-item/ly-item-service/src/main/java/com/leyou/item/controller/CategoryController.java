package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据商品分类id查询商品分类id和商品分类name Map的集合
     * @param ids
     * @return java.util.List<java.util.Map<java.lang.Long,java.lang.Object>>
     * @author vanguard
     * @date 20/4/20 18:38
     */
    @GetMapping("id/names/list")
    public ResponseEntity<List<Map<String, Object>>> queryNameMapByIds(@RequestParam("id") List<Long> ids) {
        List<Map<String, Object>> idNames = categoryService.queryIdNameMapsByIds(ids);
        return ResponseEntity.ok(idNames);
    }

    /**
     * 根据3级分类id，查询1~3级的分类
     * @param id
     * @return
     */
    @GetMapping("all/level")
    public ResponseEntity<List<Category>> queryAllByCid3(@RequestParam("id") Long id){
        List<Category> list = this.categoryService.queryAllByCid3(id);
        if (list == null || list.size() < 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

}
