package com.mikuyun.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mikuyun
 * @since 2025/2/22 19:52
 */
@Getter
@AllArgsConstructor
public enum CaptchaTypeEnum {


    /**
     * 手机验证码
     */
    PHONE(1),

    /**
     * 东西验证码
     */
    MAIL(2);

    private final Integer captchaType;

}
