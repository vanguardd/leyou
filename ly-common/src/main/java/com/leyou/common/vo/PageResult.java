package com.leyou.common.vo;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * @Title: 分页返回结果类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/10/16
 */
@Data
public class PageResult<T> {
    /** 总条数 */
    private Long total;

    /** 总页数 */
    private Integer totalPage;

    private List<T> items;

    private PageResult(PageInfo<T> pageInfo) {
        this.items = pageInfo.getList();
        this.total = pageInfo.getTotal();
        this.totalPage = pageInfo.getPages();
    }

    public PageResult(List<T> items) {
        this(new PageInfo<>(items));
    }
}
