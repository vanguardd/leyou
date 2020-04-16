package com.leyou.search.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @title: 商品服务分类client
 * @description:
 * @author: vanguard
 * @version: 1.0
 * @date: 2020/04/16
 */
@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryApi {
}
