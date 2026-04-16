package com.mikuyun.admin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mikuyun
 * @since 2025/1/25 14:16
 */
@Data
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMqProperties {

    private Boolean enabled;

    /**
     * 组名称
     */
    private String groupName;

    /**
     * nameServer
     */
    private String nameServer;

    private Integer consumeThread;

}
