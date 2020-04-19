package com.leyou.search.pojo;

/**
 * @Title: 搜索请求参数封装类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 20/4/19
 */
public class SearchRequest {
    private String key;// 搜索条件

    private Integer page;// 当前页

    private static final Integer DEFAULT_SIZE = 20;// 每页大小，不从页面接收，而是固定大小
    private static final Integer DEFAULT_PAGE = 1;// 默认页

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if(page == null){
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }
}