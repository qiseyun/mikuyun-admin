package com.mikuyun.admin.common;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Result Code Enum.
 *
 * @author qiseyun
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

    /**
     * 系统错误
     */
    SYSTEM_ERROR(500, "系统忙请稍候再试~"),

    /**
     * 登录异常
     */
    LOGIN_ERROR(30100, "登陆失败,请检查用户名或密码是否有误!"),
    USER_WRITE_OFF(30101, "该用户已注销!"),

    /**
     * 短信和验证相关业务异常.(40100-40199).
     */
    SMS_VERIFY_CODE_SENT(40101, "短信验证码已发送"),
    SMS_VERIFY_CODE_EXCEED_LIMIT(40102, "短信发送次数超限"),
    SMS_VERIFY_CODE_INVALID(40103, "请输入有效验证码"),
    SMS_VERIFY_CODE_NOT_MATCH(40104, "验证码错误，请重新输入"),
    SMS_PHONE_ERROR(40105, "请输入有效手机号码"),
    SMS_VERIFY_CODE_EXPIRE(40106, "短信验证码已失效，请重新发送"),
    SMS_VERIFY_CODE_SEND_ERROR(40107, "短信验证码发送失败，请稍后尝试"),
    SMS_NOT_ENOUGH(40108, "短信数量余额不足，请充值后重试"),
    VALIDATE_CODE_NULL(40201, "图片验证码不能为空"),
    VALIDATE_CODE_INVALID(40202, "图片验证码错误或者已过期"),

    /**
     * 邮件相关业务异常.(50100-50199).
     */
    MAIL_SEND_ERROR(50100, "邮件发送失败"),
    MAIL_SEND_FORMAT_ERROR(51100, "邮件格式错误"),

    /**
     * 服务端异常(60000-69999).
     */
    DATA_NOT_EXIST(60000, "数据不存在"),
    DATA_IS_EXIST(60100, "数据已存在"),
    SAVE_FAILED(60200, "数据保存失败"),
    UPDATE_FAILED(60300, "数据修改失败"),
    DELETE_FAILED(60400, "数据删除失败"),
    AUTH_ERROR(60500, "当前用户没有该权限"),
    PARAM_CANT_BE_NULL(60600, "参数不能为空"),
    DATA_IS_LOCK(60700, "数据已被锁住"),
    PARAM_UNDEFINED(60800, "参数未定义"),
    UPLOAD_FILE_FAILED(60900, "文件上传失败"),
    NET_WORK_ERROR(61000, "网络波动,请稍后再试"),
    PARAM_ERROR(61100, "参数错误"),
    DOWNLOAD_FILE_FAILED(61200, "文件下载失败"),
    MAIL_SENDING_FAILED(611300, "邮件发送失败");

    private final int code;

    private final String msg;

}
