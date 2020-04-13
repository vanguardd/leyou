package com.leyou.common.vo;

import com.leyou.common.enums.ExceptionEnums;
import lombok.Data;

/**
 * @Title: 自定义结果对象
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/09/24
 */
@Data
public class ExceptionResult {
    private Integer status;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnums em) {
        this.status = em.getCode();
        this.message = em.getMessage();
        this.timestamp = System.currentTimeMillis();
    }
}
