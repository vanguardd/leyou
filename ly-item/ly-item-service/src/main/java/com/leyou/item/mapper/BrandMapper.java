package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Title:
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/10/16
 */
public interface BrandMapper extends Mapper<Brand> {

    /**
     * 新增商品分类和品牌的中间表数据
     * @param cid
     * @param bid
     * @return int
     * @author vanguard
     * @date 19/10/28 20:51
     */
    @Insert("INSERT INTO tb_category_brand VALUES (#{cid}, #{bid})")
    int insertCategoryBrand(@Param("cid") Long cid, @Param("bid") Long bid);
}
