package com.leyou.cart.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Title: 商品Client
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/17
 */
@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {
}
