package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.leyou.common.exception.LyException;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Category;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Title:
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/10/16
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    public PageResult<Brand> queryPageBrandsBySearch(Integer page, Integer rows, String sortBy, Boolean desc,
                                                      String key) {
        //分页
        PageHelper.startPage(page, rows);
        //排序
        if(StringUtils.isNotBlank(sortBy)) {
            String direction = desc ? "DESC" : "ASC";
            PageHelper.orderBy(sortBy + " " + direction);
        }
        //过滤
        Example example = new Example(Brand.class);
        if(StringUtils.isNotBlank(key)) {
            example.createCriteria().andLike("name", "%" + key + "%")
                    .orEqualTo("letter", key);
        }

        //查询
        List<Brand> brands = brandMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(brands)) {
            throw new LyException(ExceptionEnums.BRAND_NOT_FOUND);
        }
        return new PageResult<>(brands);
    }

    public Brand getBrand(Long bid) {
        Brand brand = brandMapper.selectByPrimaryKey(bid);
        if(brand == null) {
            throw new LyException(ExceptionEnums.BRAND_NOT_FOUND);
        }
        return brand;
    }

    public void insert(Brand brand) {
        int count = brandMapper.insert(brand);
        if(count != 1) {
            throw new LyException(ExceptionEnums.BRAND_INSERT_ERROR);
        }
        String[] cids = brand.getCids().split(",");
        for(String cid : cids) {
            count = brandMapper.insertCategoryBrand(Long.parseLong(cid), brand.getId());
            if(count != 1) {
                throw new LyException(ExceptionEnums.BRAND_INSERT_ERROR);
            }
        }

    }

    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
        String[] cids = brand.getCids().split(",");
        for(String cid : cids) {
            Category category = categoryMapper.queryByBrandIdAndCategoryId(brand.getId(), Long.valueOf(cid));
            //如果该品牌没有该分类则保存品牌和分类关系
            if(category == null) {
                brandMapper.insertCategoryBrand(Long.valueOf(cid), brand.getId());
            }
        }
    }

    public List<Brand> getBrandsByCid(Long cid) {
        List<Brand> brandList = brandMapper.selectBrandsByCid(cid);
        if(CollectionUtils.isEmpty(brandList)) {
            throw new LyException(ExceptionEnums.BRAND_NOT_FOUND);
        }
        return brandList;

    }
}
