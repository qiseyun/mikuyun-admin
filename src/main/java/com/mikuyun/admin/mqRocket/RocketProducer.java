package com.mikuyun.admin.mqRocket;

import com.mikuyun.admin.properties.RocketMqProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/25 14:11
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RocketProducer implements InitializingBean {

    private DefaultMQProducer defaultMqProducer;

    private final RocketMqProperties rocketMqProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setProducerGroup(rocketMqProperties.getGroupName());
        defaultMQProducer.setNamesrvAddr(rocketMqProperties.getNameServer());
        defaultMQProducer.start();
        defaultMqProducer = defaultMQProducer;
        log.info("rocketmq producer start");
    }

    /**
     * 消息发送
     *
     * @param message 消息
     * @return boolean
     */
    public boolean send(Message message) {
        String content = new String(message.getBody(), StandardCharsets.UTF_8);
        try {
            message.setTopic(message.getTopic());
            SendResult result = defaultMqProducer.send(message);
            log.info("rocketmq message topic={} tag={} content={} result={}", message.getTopic(), message.getTags(), content, result);
            return result != null;
        } catch (Exception e) {
            log.error("rocketmq message error topic={} tag={} content={}", message.getTopic(), message.getTags(), content, e);
            return false;
        }
    }

    /**
     * 批量发送消息
     *
     * @param messageList 消息列表
     * @return boolean
     */
    public boolean sendBatch(List<Message> messageList) {
        try {
            SendResult result = defaultMqProducer.send(messageList);
            log.info("batch rocketmq message! result={}", result);
            return result != null;
        } catch (Exception e) {
            log.error("batch rocketmq message! error", e);
            return false;
        }
    }

}
