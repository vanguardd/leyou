package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import com.leyou.item.pojo.Spu;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
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
}
