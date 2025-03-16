package com.mikuyun.admin.exception;

import com.mikuyun.admin.common.ResultCode;
import lombok.Getter;

import java.io.Serial;

/**
 * Service exception class.
 * 自定义异常
 *
 * @author qiseyun
 * @version 1.0
 * @date 2023年3月25日/0025 0点17分
 */
@Getter
public class ServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2359767895161832954L;

    private final int code;

    public ServiceException(final String message) {
        super(message);
        this.code = ResultCode.FAILURE.getCode();
    }

    public ServiceException(final ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
    }

    public ServiceException(final ResultCode resultCode, final String msg) {
        super(msg);
        doFillInStackTrace();
        this.code = resultCode.getCode();
    }

    public ServiceException(final ResultCode resultCode, final Throwable cause) {
        super(cause);
        this.code = resultCode.getCode();
    }

    public ServiceException(final String msg, final Throwable cause) {
        super(msg, cause);
        this.code = ResultCode.FAILURE.getCode();
    }

    /**
     * fill stack trace.
     *
     * @return Throwable
     */
    public Throwable doFillInStackTrace() {
        return super.fillInStackTrace();
    }
}
