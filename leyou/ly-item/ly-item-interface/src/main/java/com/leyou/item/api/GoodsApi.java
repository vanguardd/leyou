package com.leyou.item.api;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @title: 商品API
 * @description:
 * @author: vanguard
 * @version: 1.0
 * @date: 2020/04/16
 */
@RequestMapping("goods")
public interface GoodsApi {

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
    PageResult<Spu> getSpuByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "key", required = false) String key);

    /**
     * 查询商品通用属性详情
     * @param spuId
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.SpuDetail>
     * @author vanguard
     * @date 20/1/6 21:36
     */
    @GetMapping("/spu/detail/{spuId}")
    SpuDetail detail(@PathVariable("spuId") Long spuId);

    /**
     * 查询商品特有属性集合
     * @param spuId
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.item.pojo.Sku>>
     * @author vanguard
     * @date 20/1/6 21:36
     */
    @GetMapping("/sku/list")
    List<Sku> queryBySpuId(@RequestParam("id") Long spuId);

    /**
     * 根据spu的id查询spu
     * @param id
     * @return org.springframework.http.ResponseEntity<com.leyou.item.pojo.Spu>
     * @author vanguard
     * @date 20/4/22 20:21
     */
    @GetMapping("spu/{id}")
    Spu getSpuById(@PathVariable("id") Long id);
}
