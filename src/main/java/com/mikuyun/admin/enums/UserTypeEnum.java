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
     * 超级管理员
     */
    SUPER_ADMIN(0, "超级管理员"),

    /**
     * 平台管理员
     */
    PLATFORM_ADMIN(1, "管理员"),

    /**
     * 普通用户
     */
    REGULAR_USERS(3, "普通用户");

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
