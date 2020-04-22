package com.leyou.page.service;

import com.leyou.item.pojo.*;
import com.leyou.page.client.BrandClient;
import com.leyou.page.client.CategoryClient;
import com.leyou.page.client.GoodsClient;
import com.leyou.page.client.SpecificationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Title: 商品业务
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/04/22
 */
@Service
public class GoodsService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    public Map<String, Object> loadModel(Long id) {
        Map<String, Object> map = new HashMap<>();
        // 根据id查询spu对象
        Spu spu = goodsClient.getSpuById(id);

        // 查询spuDetail
        SpuDetail detail = goodsClient.detail(id);

        // 查询sku集合
        List<Sku> skus = goodsClient.queryBySpuId(id);

        // 查询分类
        List<Long> cids = Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3());
        List<String> categoryNames = categoryClient.queryNamesByIds(cids);
        List<Map<String, Object>> categories = new ArrayList<>();
        for(int i = 0; i < cids.size(); i++) {
            Map<String, Object> categoryMap = new HashMap<>();
            categoryMap.put("id", cids.get(i));
            categoryMap.put("name", categoryNames.get(i));
            categories.add(categoryMap);
        }

        // 查询品牌
        Brand brand = brandClient.getBrand(spu.getBrandId());

        // 查询规格参数组
        List<SpecGroup> specGroups = specificationClient.getSpecsByCid(spu.getCid3());

        // 查询特殊的规格参数
        List<SpecParam> params = specificationClient.getSpecParamsByGroupId(null, spu.getCid3(), null, null);
        Map<Long, Object> paramMap = new HashMap<>();
        params.forEach(param -> {
            paramMap.put(param.getId(), param.getName());
        });

        // 封装spu
        map.put("spu", spu);
        // 封装spuDetail
        map.put("spuDetail", detail);
        // 封装sku集合
        map.put("skus", skus);
        // 分类
        map.put("categories", categories);
        // 品牌
        map.put("brand", brand);
        // 规格参数组
        map.put("groups", specGroups);
        // 查询商品的规格参数
        map.put("paramMap", paramMap);
        return map;
    }
}
