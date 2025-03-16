package com.mikuyun.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/26 15:27
 */
@Getter
@AllArgsConstructor
public enum MqTopicEnum {

    TEST("mikuyun-qing", "test", "测试消息");

    private final String rocketMqTopic;

    private final String tag;

    private final String desc;

}
