package com.mikuyun.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年3月25日/0025 10点03分
 */
@Getter
@AllArgsConstructor
public enum UserTypeEnum {

    /**
     * 普通用户
     */
    REGULAR_USERS(0, "平台用户"),

    /**
     * 平台管理员
     */
    PLATFORM_ADMIN(1, "平台管理员"),

    /**
     * 超级管理员
     */
    SUPER_ADMIN(2, "超级管理员");

    /**
     * 类型
     */
    private final Integer type;

    /**
     * 类型名称
     */
    private final String description;

    /**
     * 获取枚举类型
     *
     * @param type 枚举类型
     * @return UserTypeEnum
     */
    public static UserTypeEnum getEnumByType(Integer type) {
        if (type == null) {
            return null;
        }
        for (UserTypeEnum value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }

}
