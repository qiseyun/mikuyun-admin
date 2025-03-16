package com.mikuyun.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2022/11/1 15:40
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {

    /**
     * 男
     */
    MAN(1, "男"),

    /**
     * 女
     */
    girl(2, "女"),

    /**
     * 未知
     */
    unknown(0, "未知");

    private final Integer type;

    private final String description;

    public static GenderEnum getEnumByType(Integer type) {
        if (type == null) {
            return null;
        }
        for (GenderEnum value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        return null;
    }

}
