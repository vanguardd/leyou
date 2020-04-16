package com.leyou.search.repository;

import com.leyou.search.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @title:
 * @description:
 * @author: vanguard
 * @version: 1.0
 * @date: 2020/04/16
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
