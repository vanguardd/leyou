package com.leyou.item.service;

import com.leyou.common.exception.LyException;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Title: 商品分类业务层
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/10/04
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryListByParentId(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        List<Category> categoryList = this.categoryMapper.select(category);
        if(CollectionUtils.isEmpty(categoryList)) {
            throw new LyException(ExceptionEnums.CATEGORY_NOT_FOUND);
        }
        return categoryList;
    }

    public List<Category> queryByBrandId(Long bid) {
        return categoryMapper.queryByBrandId(bid);
    }

    public List<Category> getByIds(List<Long> ids) {
        List<Category> categoryList = categoryMapper.selectByIdList(ids);
        if(CollectionUtils.isEmpty(categoryList)) {
            throw new LyException(ExceptionEnums.CATEGORY_NOT_FOUND);
        }
        return categoryList;
    }
}
