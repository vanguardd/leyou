package com.leyou.cart.controller;

import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: 购物车相关接口
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/17
 */
@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    /**
     * 新增购物车商品信息
     * @param cart
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author vanguard
     * @date 20/5/17 21:55
     */
    @PostMapping("")
    public ResponseEntity<Void> addCart(@RequestBody Cart cart) {
        cartService.addCart(cart);
        return ResponseEntity.ok().build();
    }

    /**
     * 新增购物车商品结婚
     * @param cartList
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author vanguard
     * @date 20/5/17 21:55
     */
    @PostMapping("list")
    public ResponseEntity<Void> addCartList(@RequestBody List<Cart> cartList) {
        cartService.addCartList(cartList);
        return ResponseEntity.ok().build();
    }


    /**
     * 获得购物车商品信息
     * @param
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.cart.pojo.Cart>>
     * @author vanguard
     * @date 20/5/17 21:55
     */
    @GetMapping("")
    public ResponseEntity<List<Cart>> getCarts() {
        List<Cart> carts = cartService.getCarts();
        return ResponseEntity.ok(carts);
    }

    /**
     * 修改购物车商品信息
     * @param cart
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author vanguard
     * @date 20/5/17 21:56
     */
    @PutMapping("")
    public ResponseEntity<Void> updateCart(@RequestBody Cart cart) {
        cartService.updateCart(cart);
        return ResponseEntity.ok().build();
    }

    /**
     * 根据商品Id删除购物车商品信息
     * @param skuId
     * @return org.springframework.http.ResponseEntity<java.lang.Void>
     * @author vanguard
     * @date 20/5/17 21:56
     */
    @DeleteMapping("{skuId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("skuId") Long skuId) {
        cartService.deleteCart(skuId);
        return ResponseEntity.ok().build();
    }

    /**
     * 查询购物车商品的数量
     * @param
     * @return org.springframework.http.ResponseEntity<java.lang.Long>
     * @author vanguard
     * @date 20/5/17 21:56
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getCartCount() {
        Long count = cartService.getCartCount();
        return ResponseEntity.ok(count);
    }
}
