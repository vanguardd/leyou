package com.leyou.item.controller;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Title: 商品相关接口
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/12/17
 */
@RestController
@RequestMapping("")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 分页查询商品集合
     * @param page,
     * @param rows,
     * @param saleable,
     * @param key
     * @return org.springframework.http.ResponseEntity<com.leyou.common.vo.PageResult<com.leyou.item.pojo.Spu>>
     * @author vanguard
     * @date 20/1/6 21:35
     */
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<Spu>> getSpuByPage(
                                            @RequestParam(value = "page", defaultValue = "1") Integer page,
                                            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
                                            @RequestParam(value = "saleable", required = false) Boolean saleable,
                                            @RequestParam(value = "key", required = false) String key) {
        PageResult<Spu> spuPageResult = goodsService.getSpuByPage(page, rows, saleable, key);
        return ResponseEntity.ok(spuPageResult);
    }

    /**
     * 新增商品
     * @param spu
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author vanguard
     * @date 20/1/6 21:35
     */
    @PostMapping("/goods")
    public ResponseEntity<Void> insert(@RequestBody Spu spu) {
        goodsService.insert(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 更新商品
     * @param spu
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author vanguard
     * @date 20/1/6 21:35
     */
    @PutMapping("/goods")
    public ResponseEntity<Void> update(@RequestBody Spu spu) {
        goodsService.update(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 查询商品通用属性详情
     * @param spuId
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.SpuDetail>
     * @author vanguard
     * @date 20/1/6 21:36
     */
    @GetMapping("/spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> detail(@PathVariable("spuId") Long spuId) {
        SpuDetail spuDetail = goodsService.querySpuDetailBySpuId(spuId);
        return ResponseEntity.ok(spuDetail);
    }

    /**
     * 查询商品特有属性集合
     * @param spuId
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Sku>>
     * @author vanguard
     * @date 20/1/6 21:36
     */
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> queryBySpuId(@RequestParam("id") Long spuId) {
        List<Sku> skus = goodsService.querySkusBySpuId(spuId);
        return ResponseEntity.ok(skus);
    }
}
