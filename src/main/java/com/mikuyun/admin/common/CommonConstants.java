package com.mikuyun.admin.common;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2022/11/1 15:35
 */
public interface CommonConstants {

    /**
     * 删除
     */
    Integer STATUS_DEL_INT = 1;

    /**
     * 正常
     */
    Integer STATUS_NORMAL_INT = 0;

    /**
     * 删除
     */
    String STATUS_DEL_STR = "1";

    /**
     * 正常
     */
    String STATUS_NORMAL_STR = "0";

    /**
     * 禁用
     */
    String DISABLE_STR = "1";

    /**
     * 正常
     */
    String DISABLE_NORMAL_STR = "0";

    /**
     * 禁用
     */
    Integer DISABLE_INT = 1;

    /**
     * 正常
     */
    Integer DISABLE_NORMAL_INT = 0;

    /**
     * 下架
     */
    String OFF = "1";
    /**
     * 正常
     */
    String OFF_NORMAL = "0";

    /**
     * 锁定
     */
    String STATUS_LOCK = "9";

    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * JSON 资源
     */
    String CONTENT_TYPE = "application/json; charset=utf-8";

    /**
     * 成功标记
     */
    Integer SUCCESS = 0;

    /**
     * 成功标记
     */
    String SUCCESS_STR = "success";

    /**
     * 失败标记
     */
    Integer FAIL = 1;

    /**
     * 失败标记
     */
    String FAIL_STR = "fail";

    /**
     * 验证码前缀
     */
    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY_";

    /**
     * 默认当前页
     */
    Integer CURRENT = 1;

    /**
     * 默认每页大小
     */
    Integer SIZE = 10;

    /**
     * 邮件登录验证消息
     */
    String MAIL_LOGIN_VERIFY_MESSAGE = "详情：您正在尝试进行登录操作，若非是您本人的行为，请忽略!";

    /**
     * MIKUYUN
     */
    String MIKUYUN = "mikuyun";

}
