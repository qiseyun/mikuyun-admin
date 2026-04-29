package com.mikuyun.admin.rocketmq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mikuyun
 * @since 2025/1/26 15:27
 */
@Getter
@AllArgsConstructor
public enum TopicEnum {

    TEST("mikuyun-test", "test", "测试消息"),

    LOGIN_EMAIL("login", "email", "登录邮件"),

    CANAL_SERVER("canal_server", "canal_gxy", "文章同步"),

    ;

    private final String rocketMqTopic;

    private final String tag;

    private final String desc;

}
