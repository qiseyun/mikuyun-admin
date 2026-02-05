package com.mikuyun.admin.exception;

import com.mikuyun.admin.common.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Service exception class.
 * 自定义异常
 *
 * @author qiseyun
 * @version 1.0
 * @date 2023年3月25日/0025 0点17分
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ServiceException extends RuntimeException {

    private Integer code;

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message) {
        this(ResultCode.SYSTEM_ERROR.getCode(), message);
    }

    public ServiceException(ResultCode resultCodeEnum) {
        this(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }
}
