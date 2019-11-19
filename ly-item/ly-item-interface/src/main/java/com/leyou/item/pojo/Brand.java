package com.leyou.item.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.*;
import java.util.List;

/**
 * @Title: 品牌实体类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/10/16
 */
@Data
@Table(name = "tb_brand")
public class Brand {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    /** 品牌名称 */
    private String name;
    /** 品牌图片 */
    private String image;
    /** 品牌首字母 */
    private Character letter;

    @Transient
    private String cids;
}
