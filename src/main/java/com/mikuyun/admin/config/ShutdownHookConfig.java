package com.mikuyun.admin.config;

import com.mikuyun.admin.mqRocket.ConsumerRegister;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/25 14:44
 */
@Slf4j
@Component
public class ShutdownHookConfig implements ApplicationListener<ContextClosedEvent> {

    @Resource
    private ConsumerRegister consumerRegister;

    @Override
    public void onApplicationEvent(@NotNull ContextClosedEvent contextClosedEvent) {
        log.info("satokenApplication pre shutdown");
        List<DefaultMQPushConsumer> consumerList = consumerRegister.getConsumerList();
        for (DefaultMQPushConsumer pushConsumer : consumerList) {
            pushConsumer.shutdown();
            log.info("rocketMQ consumer shutdown");
        }
    }

}
