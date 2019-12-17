package com.leyou.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Title: 异常枚举类
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/09/24
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEnums {

    /**
     * 校验商品价格
     */
    PRICE_CANNOT_BE_NULL(400, "价格不能为空!"),
    CATEGORY_NOT_FOUND(404, "商品分类不存在！"),
    BRAND_NOT_FOUND(404, "品牌不存在！"),
    SPEC_GROUP_NOT_FOUND(404, "规格参数组不存在！"),
    SPEC_PARAM_NOT_FOUND(404, "规格参数不存在！"),
    GOODS_NOT_FOUND(404, "商品不存在！"),
    BRAND_INSERT_ERROR(500, "新增品牌失败！"),
    UPLOAD_FILE_ERROR(500, "上传文件失败！"),
    INVALID_FILE_TYPE(400, "无效的文件类型"),
    ;
    private Integer code;
    private String message;
}
