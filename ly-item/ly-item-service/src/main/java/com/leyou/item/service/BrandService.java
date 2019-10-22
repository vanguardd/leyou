package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.leyou.common.LyException;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
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

    public PageResult<Brand> queryPageBrandsBySearch(Integer page, Integer rows, String sortBy, Boolean desc,
                                                      String key) {
        PageHelper.startPage(page, rows);

        Example example = new Example(Brand.class);
        if(StringUtils.isNotBlank(key)) {
            example.createCriteria().andLike("name", "%" + key + "%")
                    .orEqualTo("letter", key);
        }
        if(StringUtils.isNotBlank(sortBy)) {
            String direction = desc ? "ASC" : "DESC";
            PageHelper.orderBy(sortBy + " " + direction);
        }
        List<Brand> brands = brandMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(brands)) {
            throw new LyException(ExceptionEnums.BRAND_NOT_FOUND);
        }
        return new PageResult<>(brands);
    }

    public Brand getBrand(Long bid) {
        return brandMapper.selectByPrimaryKey(bid);
    }

    public void insert(Brand brand) {
        brandMapper.insert(brand);
    }

    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }
}
