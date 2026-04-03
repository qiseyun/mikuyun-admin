package com.mikuyun.admin.mqRocket;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/26 15:27
 */
@Getter
@AllArgsConstructor
public enum AsyncMessageTypeEnum {

    /**
     * demo_1
     */
    DEMO_ONE("demo_one", "测试消息"),

    ;

    private final String type;

    private final String desc;

}
