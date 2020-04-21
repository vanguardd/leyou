package com.leyou.search.pojo;

import lombok.Data;

import java.util.Map;

/**
 * @Title: 搜索请求参数封装类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 20/4/19
 */
@Data
public class SearchRequest {
    /**
     * 搜索条件
     */
    private String key;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 排序字段
     */
    private String sortBy;

    /**
     * 是否降序
     */
    private Boolean descending;

    /**
     * 过滤条件
     */
    private Map<String, String> filter;

    private static final Integer DEFAULT_SIZE = 20;// 每页大小，不从页面接收，而是固定大小
    private static final Integer DEFAULT_PAGE = 1;// 默认页

    public Integer getPage() {
        if(page == null){
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }
}