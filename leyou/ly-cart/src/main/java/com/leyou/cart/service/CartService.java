package com.leyou.cart.service;

import com.leyou.auth.entity.UserInfo;
import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.pojo.Sku;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Title: 购物车业务逻辑
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/17
 */
@Slf4j
@Service
public class CartService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private GoodsClient goodsClient;

    static final String KEY_PREFIX = "leyou:cart:uid";

    /**
     * 添加购物车商品
     * @param cart
     * @return void
     * @author vanguard
     * @date 20/5/17 16:12
     */
    public void addCart(Cart cart) {
        // 获得当前登录的用户
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        // 存储购物车数据Redis的Key
        String key = KEY_PREFIX + userInfo.getId();
        // 获取hash操作对象
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(key);
        // 查询redis中是否存在该商品
        Long skuId = cart.getSkuId();
        Integer num = cart.getNum();
        Boolean isExist = hashOps.hasKey(skuId.toString());
        // 存在，则更新商品数量
        if(isExist != null && isExist) {
            String json = Objects.requireNonNull(hashOps.get(skuId.toString())).toString();
            cart = JsonUtils.toBean(json, Cart.class);
            // 更新商品数量
            cart.setNum(cart.getNum() + num);
        } else {
            // 不存在，则将商品数据存入redis中
            cart.setUserId(userInfo.getId());
            // 根据商品Id查询商品信息
            Sku sku = goodsClient.getSkuById(skuId);
            cart.setTitle(sku.getTitle());
            cart.setPrice(sku.getPrice());
            cart.setImage(StringUtils.isBlank(sku.getImages()) ? "" : StringUtils.split(sku.getImages(), ",")[0]);
            cart.setNum(num);
            cart.setOwnSpec(sku.getOwnSpec());
            cart.setSpuId(sku.getSpuId());
        }
        hashOps.put(skuId.toString(), JsonUtils.toString(cart));
    }

    /**
     * 查询登录登录用户的购物车商品集合
     * @param
     * @return java.util.List<com.leyou.cart.pojo.Cart>
     * @author vanguard
     * @date 20/5/17 17:58
     */
    public List<Cart> getCarts() {
        // 获得当前登录的用户
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        // 存储购物车数据Redis的Key
        String key = KEY_PREFIX + userInfo.getId();
        // 是否存在购物车
        if(!redisTemplate.hasKey(key)) {
            throw new LyException(ExceptionEnums.CART_NOT_FOUND);
        }
        // 获取hash操作对象
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(key);
        // 查询当前登录用户的购物车商品集合
        List<Object> carts = hashOps.values();
        // 判断是否有数据
        if(CollectionUtils.isEmpty(carts)) {
            throw new LyException(ExceptionEnums.CART_NOT_FOUND);
        }
        return carts.stream().map(o -> JsonUtils.toBean(o.toString(), Cart.class)).collect(Collectors.toList());
    }

    /**
     * 更新购物车商品信息
     * @param cart
     * @return void
     * @author vanguard
     * @date 20/5/17 18:05
     */
    public void updateCart(Cart cart) {
        // 获得当前登录的用户
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        // 存储购物车数据Redis的Key
        String key = KEY_PREFIX + userInfo.getId();
        // 获取hash操作对象
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(key);
        // 查询redis中是否存在该商品
        Long skuId = cart.getSkuId();
        String json = Objects.requireNonNull(hashOps.get(skuId.toString())).toString();
        cart = JsonUtils.toBean(json, Cart.class);
        // 更新商品数量
        cart.setNum(cart.getNum());
        hashOps.put(skuId.toString(), JsonUtils.toString(cart));
    }

    /**
     * 根据商品Id删除购物车商品信息
     * @param skuId
     * @return void
     * @author vanguard
     * @date 20/5/17 20:26
     */
    public void deleteCart(Long skuId) {
        // 获得当前登录的用户
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        // 存储购物车数据Redis的Key
        String key = KEY_PREFIX + userInfo.getId();
        // 获取hash操作对象
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(key);
        // 删除对应商品信息
        hashOps.delete(skuId.toString());
    }

    /**
     * 批量新增购物车商品信息
     * @param cartList
     * @return void
     * @author vanguard
     * @date 20/5/17 20:43
     */
    public void addCartList(List<Cart> cartList) {
        cartList.forEach(this::addCart);
    }

    /**
     * 查询购物车商品的数量
     * @param
     * @return java.lang.Long
     * @author vanguard
     * @date 20/5/17 21:23
     */
    public Long getCartCount() {
        // 获得当前登录的用户
        UserInfo userInfo = LoginInterceptor.getUserInfo();
        // 存储购物车数据Redis的Key
        String key = KEY_PREFIX + userInfo.getId();
        // 是否存在购物车
        if(!redisTemplate.hasKey(key)) {
            throw new LyException(ExceptionEnums.CART_NOT_FOUND);
        }
        // 获取hash操作对象
        BoundHashOperations<String, Object, Object> hashOps = redisTemplate.boundHashOps(key);
        return hashOps.size();

    }
}
