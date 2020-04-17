package com.leyou.item.controller;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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

    /**
     * 分页查询品牌
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return org.springframework.http.ResponseEntity<com.leyou.common.vo.PageResult<com.leyou.item.pojo.Brand>>
     * @author vanguard
     * @date 19/10/21 21:38
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryPageBrandBySearch(
                                                                    @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                    @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                                                    @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                    @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
                                                                    @RequestParam(value = "key", required = false) String key) {
        PageResult<Brand> pageResult = brandService.queryPageBrandsBySearch(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 根据id查询品牌
     * @param bid
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Brand>
     * @author vanguard
     * @date 19/10/21 21:38
     */
    @GetMapping("bid/{bid}")
    public ResponseEntity<Brand> getBrand(@PathVariable("bid") Long bid) {
        Brand brand = brandService.getBrand(bid);
        return ResponseEntity.ok(brand);
    }

    /**
     * 新增品牌
     * @param brand
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Brand>
     * @author vanguard
     * @date 19/10/21 21:39
     */
    @PostMapping()
    public ResponseEntity<Void> insert(@RequestBody Brand brand) {
        brandService.insert(brand);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更新品牌
     * @param brand
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Brand>
     * @author vanguard
     * @date 19/10/21 21:39
     */
    @PutMapping()
    public ResponseEntity<Brand> update(@RequestBody Brand brand) {
        brandService.update(brand);
        return ResponseEntity.ok(brand);
    }

    /**
     * 根据商品分类id查询品牌集合
     * @param cid
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Brand>>
     * @author vanguard
     * @date 20/1/2 20:34
     */
    @GetMapping("/cid/{cid}")
    public ResponseEntity<List<Brand>> getBrandsByCid(@PathVariable("cid") Long cid) {
        List<Brand> brandList = brandService.getBrandsByCid(cid);
        return ResponseEntity.ok(brandList);
    }
}
