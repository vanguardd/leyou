package com.leyou.search.service;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.SpecParam;
import com.leyou.search.client.BrandClient;
import com.leyou.search.client.CategoryClient;
import com.leyou.search.client.SpecificationClient;
import com.leyou.search.pojo.Goods;
import com.leyou.search.pojo.SearchRequest;
import com.leyou.search.pojo.SearchResult;
import com.leyou.search.repository.GoodsRepository;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.AggregatorBase;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.UnmappedTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private SpecificationClient specificationClient;


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
        MatchQueryBuilder basicQuery = QueryBuilders.matchQuery("all", key).operator(Operator.AND);
        queryBuilder.withQuery(basicQuery);

        // 2. 通过sourceFilter设置返回的结果字段，我们只需要id、skus、subTitle
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "skus", "subTitle"}, null));

        // 3. 分页
        // 准备分页参数
        int page = searchRequest.getPage();
        int size = searchRequest.getSize();
        queryBuilder.withPageable(PageRequest.of(page - 1, size));

        String categoryAggName = "categories";
        String brandAggName = "brands";
        queryBuilder.addAggregation(AggregationBuilders.terms(categoryAggName).field("cid3"));
        queryBuilder.addAggregation(AggregationBuilders.terms(brandAggName).field("brandId"));

        //4. 排序
        String sortBy = searchRequest.getSortBy();
        Boolean desc = searchRequest.getDescending();
        if(StringUtils.isNotBlank(sortBy)) {
            queryBuilder.withSort(SortBuilders.fieldSort(sortBy).order(desc ? SortOrder.DESC : SortOrder.ASC));
        }

        // 4, 查询，获取结果
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>) goodsRepository.search(queryBuilder.build());
        // 搜索为空时，搜索结果不存在
        if(goodsPage == null) {
            throw new LyException(ExceptionEnums.GOODS_NOT_FOUND);
        }

        // 解析聚合结果集
        List<Map<String, Object>> categories = getCategoryAggResult(goodsPage.getAggregation(categoryAggName));
        List<Brand> brands = getBrandAggResult(goodsPage.getAggregation(brandAggName));

        // 判断分类聚合结果集大小，等于1时则聚合
        List<Map<String, Object>> specs = null;
        assert categories != null;
        if(categories.size() == 1) {
            specs = getParamAggResult((Long)categories.get(0).get("id"), basicQuery);
        }
        return new SearchResult(goodsPage.getContent(), goodsPage.getTotalElements(), goodsPage.getTotalPages(), categories, brands, specs);

    }

    /**
     * 聚合出规格参数过滤条件
     * @param id
     * @param basicQuery
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author vanguard
     * @date 20/4/20 23:11
     */
    private List<Map<String, Object>> getParamAggResult(Long id, QueryBuilder basicQuery) {
        //创建自动义查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withQuery(basicQuery);
        //添加聚合
        List<SpecParam> specParams = specificationClient.getSpecParamsByGroupId(null, id, null, null);
        specParams.forEach(specParam -> {
            queryBuilder.addAggregation(AggregationBuilders.terms(specParam.getName()).field("specs." + specParam.getName() + ".keyword"));
        });
        //只需要聚合结果集，不需要查询结果集
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{}, null));
        //执行结果集
        AggregatedPage<Goods> goodsPage = (AggregatedPage<Goods>) goodsRepository.search(queryBuilder.build());

        //定义一个集合，收集聚合结果集
        List<Map<String, Object>> paramMapList = new ArrayList<>();
        Map<String, Aggregation> aggregationMap = goodsPage.getAggregations().asMap();
        for (Map.Entry<String, Aggregation> entry : aggregationMap.entrySet()) {
            Map<String, Object> map = new HashMap<>();
            // 放入规格参数名
            map.put("k", entry.getKey());
            // 收集规格参数值
            List<Object> options = new ArrayList<>();
            if(entry.getValue() instanceof UnmappedTerms) {
                continue;
            }
            // 解析每个聚合
            StringTerms terms = (StringTerms)entry.getValue();
            // 遍历每个聚合中桶，把桶中key放入收集规格参数的集合中
            terms.getBuckets().forEach(bucket -> options.add(bucket.getKeyAsString()));
            map.put("options", options);
            paramMapList.add(map);
        }
        return paramMapList;
    }

    /**
     * 解析分类聚合结果集
     * @param aggregation
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
     * @author vanguard
     * @date 20/4/20 21:05
     */
    private List<Map<String, Object>> getCategoryAggResult(Aggregation aggregation) {
        //处理聚合结果集
        LongTerms categoryTerm = (LongTerms) aggregation;
        //获取所有的分类id桶
        List<LongTerms.Bucket> buckets = categoryTerm.getBuckets();
        List<Long> cids = new ArrayList<>();
        List<Map<String, Object>> categories = new ArrayList<>();
        buckets.forEach(bucket -> cids.add(bucket.getKeyAsNumber().longValue()));
        if(cids.size() == 0) {
            return null;
        }
        //return categoryClient.queryNameMapByIds(cids);
        List<String> names = this.categoryClient.queryNamesByIds(cids);
        for (int i = 0; i < cids.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", cids.get(i));
            map.put("name", names.get(i));
            categories.add(map);
        }
        return categories;
    }

    /**
     * 解析品牌聚合结果集
     * @param aggregation
     * @return java.util.List<com.leyou.item.pojo.Brand>
     * @author vanguard
     * @date 20/4/20 21:26
     */
    private List<Brand> getBrandAggResult(Aggregation aggregation) {
        LongTerms brandTerm = (LongTerms) aggregation;
        List<LongTerms.Bucket> buckets = brandTerm.getBuckets();
        List<Brand> brands = new ArrayList<>();
        buckets.forEach(bucket -> {
            Brand brand = brandClient.getBrand(bucket.getKeyAsNumber().longValue());
            brands.add(brand);
        });
        return brands;
//        List<Long> brandIds = brandTerm.getBuckets().stream().map(bucket -> bucket.getKeyAsNumber().longValue()).collect(Collectors.toList());
//        return brandIds.stream().map(id -> brandClient.getBrand(id)).collect(Collectors.toList());
//
//        return brandTerm.getBuckets().stream().map(bucket ->
//                brandClient.getBrand(bucket.getKeyAsNumber().longValue())).collect(Collectors.toList());
    }


}
