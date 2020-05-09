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
    INVALID_FILE_TYPE(400, "无效的文件类型"),
    GOODS_ID_CANNOT_BE_NULL(400, "商品id不能为空！"),
    INVALID_CHECK_DATA_TYPE(400, "数据校验参数有误"),
    USERNAME_OR_PASSWORD_ERROR(400, "用户名或密码错误"),
    USER_REGISTER_VERIFY_CODE_ERR(401, "验证码不正确！"),
    CATEGORY_NOT_FOUND(404, "商品分类不存在！"),
    BRAND_NOT_FOUND(404, "品牌不存在！"),
    SPEC_GROUP_NOT_FOUND(404, "规格参数组不存在！"),
    SPEC_PARAM_NOT_FOUND(404, "规格参数不存在！"),
    GOODS_NOT_FOUND(404, "商品不存在！"),
    GOODS_DETAIL_NOT_FOUND(404, "商品详情不存在！"),
    GOODS_SKU_NOT_FOUND(404, "商品sku不存在！"),
    BRAND_INSERT_ERROR(500, "新增品牌失败！"),
    UPLOAD_FILE_ERROR(500, "上传文件失败！"),
    GOODS_SAVE_ERROR(500, "商品保存失败！"),
    GOODS_UPDATE_ERROR(500, "商品更新失败！"),
    SEND_VERIFY_CODE_FAILED(500, "发送验证码失败！"),
    USER_LOGIN_ERROR(500, "用户登录失败，请稍后重试！"),
    USER_VERIFY_ERROR(500, "用户信息校验失败，请稍后重试！"),

    ;
    private Integer code;
    private String message;
}
