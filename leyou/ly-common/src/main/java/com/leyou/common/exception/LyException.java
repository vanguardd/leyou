package com.leyou.common.exception;

import com.leyou.common.enums.ExceptionEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Title: 自定义异常
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/09/24
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LyException extends RuntimeException {
    private ExceptionEnums exceptionEnums;
}
