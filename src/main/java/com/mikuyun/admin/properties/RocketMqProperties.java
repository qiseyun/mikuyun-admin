package com.mikuyun.admin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/25 14:16
 */
@Data
@Component
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMqProperties {

    /**
     * 组名称
     */
    private String groupName;

    /**
     * nameServer
     */
    private String nameServer;

    /**
     * 是否环境隔离
     */
    private Boolean environmentalIsolation;

    /**
     * tag后缀
     */
    private String topicPrefix;

    private Integer consumeThread;

}
