package com.mikuyun.admin.exception;

import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2022/11/1 14:35
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 全局异常拦截
    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        log.error(e.getMessage());
        return SaResult.error(e.getMessage());
    }

}
