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
     * RocketMQ Proxy gRPC 端点地址 (如 localhost:8071)
     */
    private String endpoint;

    private Integer consumeThread;

}
