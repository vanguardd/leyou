package com.leyou.order.enums;

/**
 * @Title: 订单状态
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2020/05/26
 */
public enum OrderStatusEnum {

    UN_PAY(1, "未付款"),
    PAYED(2, "已付款，未发货"),
    DELIVERED(3, "已发货，未确认"),
    SUCCESS(4, "已确认，未评价"),
    CLOSED(5, "交易失败，已关闭"),
    RATED(6, "已评价")
    ;

    private Integer status;

    private String desc;

    OrderStatusEnum(Integer status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int value() {
        return this.status;
    }
}
