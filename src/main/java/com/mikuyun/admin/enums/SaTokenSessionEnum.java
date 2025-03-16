package com.mikuyun.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2024/8/2 下午7:44
 */
@Getter
@AllArgsConstructor
public enum SaTokenSessionEnum {

    /**
     * 用户
     */
    USER("user", "用户", "user_session"),

    /**
     * 管理员
     */
    ADMIN("admin", "管理员", "admin_session");

    private final String stpLoginIc;

    private final String desc;

    private final String sessionKey;

}
