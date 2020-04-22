package com.leyou.page.client;

import com.leyou.item.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @title: 商品服务品牌client
 * @description:
 * @author: vanguard
 * @version: 1.0
 * @date: 2020/04/16
 */
@FeignClient(value = "item-service")
public interface BrandClient extends BrandApi {
}
