package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

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

    /**
     * 根据商品分类查询品牌
     * @param cid
     * @return java.util.List<com.leyou.item.pojo.Brand>
     * @author vanguard
     * @date 20/1/2 20:24
     */
    @Select("SELECT b.* FROM tb_brand b LEFT JOIN tb_category_brand cb ON b.id=cb.brand_id WHERE cb.category_id=#{cid}")
    List<Brand> selectBrandsByCid(@Param("cid") Long cid);
}
