package com.mikuyun.admin.rocketmq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mikuyun
 * @since 2025/1/26 15:27
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
