package com.mikuyun.admin.exception;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * @author mikuyun
 * @since 2022/11/1 14:35
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public R<Void> handlerException(Exception e) {
        log.error("全局异常拦截: {}", e.getMessage(), e);
        if (e instanceof NoResourceFoundException) {
            return R.error(ResultCode.NOT_FOUND);
        }
        if (e instanceof SaTokenException) {
            if (ResultCode.getTokenErrorCode().contains(((SaTokenException) e).getCode())) {
                return R.error(((SaTokenException) e).getCode(), e.getMessage());
            }
        }
        return R.error(ResultCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = NotPermissionException.class)
    public R<Void> handlerException(NotPermissionException e) {
        log.error("全局异常拦截: {}", e.getMessage(), e);
        return R.error(ResultCode.NOT_PERMISSION);
    }

    @ExceptionHandler(value = NotRoleException.class)
    public R<Void> handlerException(NotRoleException e) {
        log.error("全局异常拦截: {}", e.getMessage(), e);
        return R.error(ResultCode.NOT_ROLE);
    }

}
