package com.leyou.search.service;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.repository.GoodsRepository;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;


/**
 * @Title: 搜索业务
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/04/18
 */
@Service
public class SearchService {

    @Autowired
    private GoodsRepository goodsRepository;


    /**
     * 商品搜索业务
     * @param searchRequest 搜索参数
     * @return com.leyou.common.vo.PageResult<com.leyou.search.pojo.Goods>
     * @author vanguard
     * @date 20/4/19 20:14
     */
    public PageResult<Goods> search(SearchRequest searchRequest) {
        String key = searchRequest.getKey();
        // 判断是否有搜索条件，如果没有，直接返回null，不允许搜索全部商品
        if(StringUtils.isBlank(key)) {
            return null;
        }

        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        // 1. 对key进行全文检索查询
        queryBuilder.withQuery(QueryBuilders.fuzzyQuery("all", key));

        // 2. 通过sourceFilter设置返回的结果字段，我们只需要id、skus、subTitle
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "skus", "subTitle"}, null));

        // 3. 分页
        // 准备分页参数
        int page = searchRequest.getPage();
        int size = searchRequest.getSize();
        queryBuilder.withPageable(PageRequest.of(page - 1, size));

        //4. 排序
        String sortBy = searchRequest.getSortBy();
        Boolean desc = searchRequest.getDescending();
        if(StringUtils.isNotBlank(sortBy)) {
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
        }

        // 4, 查询，获取结果
        Page<Goods> goods = goodsRepository.search(queryBuilder.build());
        // 搜索为空时，搜索结果不存在
        if(goods == null) {
            throw new LyException(ExceptionEnums.GOODS_NOT_FOUND);
        }
        return new PageResult<>(goods.getTotalElements(), goods.getTotalPages(), goods.getContent());

    }
}
