package com.mikuyun.admin.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/28 20:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
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
