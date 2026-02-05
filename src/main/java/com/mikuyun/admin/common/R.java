package com.mikuyun.admin.common;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * 响应信息主体
 *
 * @author mikuyun
 * @version 1.0
 * @date 2023年3月25日/0025 0点17分
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class R<T> {

    private int code;

    private String msg;

    private T data;

    public static <T> R<T> ok() {
        return restResult(null, Constant.SUCCESS, "success");
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, Constant.SUCCESS, Constant.SUCCESS_STR);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, Constant.SUCCESS, msg);
    }

    public static <T> R<T> failed() {
        return restResult(null, Constant.FAIL, Constant.FAIL_STR);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, Constant.FAIL, msg);
    }

    public static <T> R<T> failed(T data) {
        return restResult(data, Constant.FAIL, Constant.FAIL_STR);
    }

    public static <T> R<T> failed(T data, String msg) {
        return restResult(data, Constant.FAIL, msg);
    }

    public static <T> R<T> error(int code, String msg) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> error(ResultCode resultCode) {
        return restResult(null, resultCode.getCode(), resultCode.getMsg());
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> result = new R<>();
        result.setCode(code);
        result.setData(data);
        result.setMsg(msg);
        return result;
    }

}
