package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SkuMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.mapper.StockMapper;
import com.leyou.item.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Title: 商品相关业务
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/12/17
 */
@Service
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    public PageResult<Spu> getSpuByPage(Integer page, Integer rows, Boolean saleable, String key) {
        //分页
        PageHelper.startPage(page, rows);

        //默认排序
        PageHelper.orderBy("last_update_time DESC");
        //过滤
        Example example = new Example(Spu.class);
        if(saleable != null) {
            example.createCriteria().andEqualTo("saleable", saleable);
        }
        if(StringUtils.isNotBlank(key)) {
            example.createCriteria().andLike("title", "%" + key + "%");
        }
        List<Spu> spus = spuMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(spus)) {
            throw new LyException(ExceptionEnums.GOODS_NOT_FOUND);
        }

        loadCategoryNameAndBrandName(spus);

        return new PageResult<>(spus);
    }

    private void loadCategoryNameAndBrandName(List<Spu> spuList) {
        for(Spu spu : spuList) {
            //处理分类名称
            List<String> names = categoryService.getByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()))
                    .stream().map(Category::getName).collect(Collectors.toList());
            spu.setCname(StringUtils.join(names, "/"));

            //处理品牌名称
            Brand brand = brandService.getBrand(spu.getBrandId());
            spu.setBname(brand.getName());
        }
    }

    /**
     * 新增商品
     * @param spu
     * @return void
     * @author vanguard
     * @date 20/1/2 21:07
     */
    @Transactional
    public void insert(Spu spu) {
        //新增spu
        //设置默认属性
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(spu.getCreateTime());
        spu.setSaleable(true);
        spu.setValid(true);
        int count = spuMapper.insert(spu);
        if(count != 1) {
            throw new LyException(ExceptionEnums.GOODS_SAVE_ERROR);
        }

        //新增spuDetail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        spuDetailMapper.insert(spuDetail);

        saveSkuAndStock(spu);

    }



    /**
     * 根据spuId查询商品通用属性详情
     * @param spuId
     * @return com.leyou.item.pojo.SpuDetail
     * @author vanguard
     * @date 20/1/6 20:39
     */
    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(spuId);
        if(spuDetail == null) {
            throw new LyException(ExceptionEnums.GOODS_DETAIL_NOT_FOUND);
        }
        return spuDetail;

    }

    /**
     * 根据spuId查询sku的集合
     * @param spuId
     * @return java.util.List<com.leyou.item.pojo.Sku>
     * @author vanguard
     * @date 20/1/6 20:52
     */
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skuList = skuMapper.select(sku);
        if(CollectionUtils.isEmpty(skuList)) {
            throw new LyException(ExceptionEnums.GOODS_SKU_NOT_FOUND);
        }
        //获得spuId集合
        List<Long> ids = skuList.stream().map(Sku::getId).collect(Collectors.toList());
        //根据spuId集合查询库存集合
        List<Stock> stockList = stockMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(stockList)) {
            throw new LyException(ExceptionEnums.GOODS_SKU_NOT_FOUND);
        }
        //将库存集合转化成 key:SkuId,value:stock 的Map
        Map<Long, Integer> stockMap = stockList.stream().collect(Collectors.toMap(Stock::getSkuId, Stock::getStock));
        skuList.forEach(s -> s.setStock(stockMap.get(s.getId())));
        return skuList;

    }

    /**
     * 更新商品信息
     * @param spu
     * @return void
     * @author vanguard
     * @date 20/1/6 21:14
     */
    @Transactional
    public void update(Spu spu) {
        if(spu.getId() == null) {
            throw new LyException(ExceptionEnums.GOODS_ID_CANNOT_BE_NULL);
        }
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        //查询skus
        List<Sku> skuList = skuMapper.select(sku);
        //如果存在skus，则删除
        if(!CollectionUtils.isEmpty(skuList)) {
            //删除sku
            skuMapper.delete(sku);
            //删除stock
            List<Long> ids = skuList.stream().map(Sku::getId).collect(Collectors.toList());
            stockMapper.deleteByIdList(ids);
        }
        //设置spu默认属性
        spu.setValid(true);
        spu.setSaleable(true);
        spu.setLastUpdateTime(new Date());
        //修改spu
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if(count != 1) {
            throw new LyException(ExceptionEnums.GOODS_UPDATE_ERROR);
        }
        count = spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
        if(count != 1) {
            throw new LyException(ExceptionEnums.GOODS_UPDATE_ERROR);
        }

        saveSkuAndStock(spu);

    }

    /**
     * 新增商品特有属性和库存
     * @param spu
     * @return void
     * @author vanguard
     * @date 20/1/6 21:28
     */
    private void saveSkuAndStock(Spu spu) {
        int count;

        //新增sku
        List<Sku> skus = spu.getSkus();
        List<Stock> stocks = new ArrayList<>();
        for(Sku sku : skus) {
            sku.setSpuId(spu.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(sku.getCreateTime());
            sku.setEnable(true);

            count = skuMapper.insert(sku);
            if(count != 1) {
                throw new LyException(ExceptionEnums.GOODS_SAVE_ERROR);
            }
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stocks.add(stock);

        }
        //新增stock
        count = stockMapper.insertList(stocks);
        if(stocks.size() != count) {
            throw new LyException(ExceptionEnums.GOODS_SAVE_ERROR);
        }
    }

    public Spu getSpuById(Long id) {

        Spu spu = spuMapper.selectByPrimaryKey(id);
        if(spu == null) {
            throw new LyException(ExceptionEnums.GOODS_NOT_FOUND);
        }
        return spu;
    }
}
