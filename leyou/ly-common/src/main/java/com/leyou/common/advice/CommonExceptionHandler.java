package com.leyou.common.advice;

import com.leyou.common.exception.LyException;
import com.leyou.common.enums.ExceptionEnums;
import com.leyou.common.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @Title: 通用异常切面
 * @Description:
 * @Author: vanguard
 * @Version: 1.0
 * @Date: 2019/09/24
 */
@ControllerAdvice
public class CommonExceptionHandler {
    @ExceptionHandler(LyException.class)
    public ResponseEntity<ExceptionResult> handlerException(LyException e) {
        ExceptionEnums em = e.getExceptionEnums();
        return ResponseEntity.status(em.getCode()).body(new ExceptionResult(em));
    }
}
