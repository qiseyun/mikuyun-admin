package com.mikuyun.admin.config;

import com.mikuyun.admin.mqRocket.ConsumerRegister;
import com.mikuyun.admin.mqRocket.RocketProducer;
import com.mikuyun.admin.properties.RocketMqProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/12/3 15:24
 */
@Configuration
@EnableConfigurationProperties(RocketMqProperties.class)
@ConditionalOnProperty(prefix = "rocketmq", name = "enabled", havingValue = "true", matchIfMissing = true)
public class RocketMQAutoConfiguration {

    @Bean
    public RocketProducer rocketProducer(RocketMqProperties props) {
        return new RocketProducer(props);
    }

    @Bean
    public ConsumerRegister consumerRegister(RocketMqProperties props, ApplicationContext ctx) {
        return new ConsumerRegister(props, ctx);
    }

}
