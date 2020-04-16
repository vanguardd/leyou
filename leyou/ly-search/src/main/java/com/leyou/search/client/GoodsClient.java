package com.leyou.search.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @title: 商品服务商品client
 * @description:
 * @author: vanguard
 * @version: 1.0
 * @date: 2020/04/16
 */
@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsApi {
}
