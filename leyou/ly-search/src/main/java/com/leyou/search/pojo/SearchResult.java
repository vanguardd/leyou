package com.leyou.search.pojo;

import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @Title: 商品搜索结果封装类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/04/20
 */
@Getter@Setter
public class SearchResult extends PageResult<Goods> {

    /**
     * 商品分类集合
     */
    private List<Map<String, Object>> categories;

    /**
     * 品牌集合
     */
    private List<Brand> brands;

    /**
     * 规格参数集合
     */
    private List<Map<String, Object>> specs;

    public SearchResult(List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    public SearchResult(List<Goods> items, Long total, List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }

    public SearchResult(List<Goods> items, Long total, Integer totalPage, List<Map<String, Object>> categories, List<Brand> brands, List<Map<String, Object>> specs) {
        super(total, totalPage, items);
        this.categories = categories;
        this.brands = brands;
        this.specs = specs;
    }
}
