package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Title:
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/10/04
 */
public interface CategoryMapper extends Mapper<Category>, IdListMapper<Category, Long> {

    /**
     * 根据品牌id查询商品分类
     * @param bid
     * @return java.util.List<com.leyou.item.pojo.Category>
     * @author vanguard
     * @date 19/11/25 20:26
     */
    @Select("SELECT * FROM tb_category WHERE id IN (SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid})")
    List<Category> queryByBrandId(Long bid);

    /**
     * 根据品牌Id和分类Id查询该品牌对应的某个分类
     * @param bid
     * @param cid
     * @return com.leyou.item.pojo.Category
     * @author vanguard
     * @date 19/11/26 21:18
     */
    @Select("SELECT * FROM tb_category WHERE id IN (SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid} AND category_id = #{cid})")
    Category queryByBrandIdAndCategoryId(Long bid, Long cid);
}
