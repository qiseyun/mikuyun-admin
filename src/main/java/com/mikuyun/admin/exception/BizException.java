package com.mikuyun.admin.exception;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/28 20:37
 */
public class BizException extends RuntimeException {

    private int code;

    public BizException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BizException(String message) {
        super(message);
        this.code = -1;
    }

}
