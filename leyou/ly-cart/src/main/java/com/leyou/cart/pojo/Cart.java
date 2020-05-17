package com.leyou.cart.pojo;

import lombok.Data;

/**
 * @Title: 购物车实体
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/17
 */
@Data
public class Cart {

    /**
     *  用户Id
     */
    private Long userId;

    /**
     *  商品特有属性Id
     */
    private Long skuId;

    /**
     *  商品通用属性Id
     */
    private Long spuId;

    /**
     *  商品标题
     */
    private String title;

    /**
     *  商品图片
     */
    private String image;

    /**
     *  加入购物车时的价格
     */
    private Long price;

    /**
     *  购物车商品数量
     */
    private Integer num;

    /**
     *  商品规格参数
     */
    private String ownSpec;
}
