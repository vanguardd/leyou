package com.leyou.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @title: 商品索引库实体类
 * @description:
 * @author: vanguard
 * @version: 1.0
 * @date: 2020/04/16
 */
@Data
@Document(indexName = "goods", type = "docs", shards = 1, replicas = 0)
public class Goods {
    /**
     * spuId
     */
    @Id
    private Long id;

    /**
     * 所有需要被搜索的信息，包含标题，分类，甚至品牌
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String all;

    /**
     * 卖点
     */
    @Field(type = FieldType.Keyword, index = false)
    private String subTitle;

    /**
     * 品牌id
     */
    private Long brandId;

    /**
     * 1级分类id
     */
    private Long cid1;

    /**
     * 2级分类id
     */
    private Long cid2;

    /**
     * 3级分类id
     */
    private Long cid3;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 价格数组，是所有sku的价格集合。方便根据价格进行筛选过滤
     */
    private List<Long> price;

    /**
     * List<sku>信息的json结构
     * 用于页面展示的sku信息，不索引，不搜索。包含skuId、image、price、title字段
     */
    @Field(type = FieldType.Keyword, index = false)
    private String skus;

    /**
     * 可搜索的规格参数，key是参数名，值是参数值
     */
    private Map<String, Object> specs;

}
