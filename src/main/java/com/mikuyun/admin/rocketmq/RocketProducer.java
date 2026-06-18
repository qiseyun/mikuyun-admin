package com.mikuyun.admin.rocketmq;

import com.mikuyun.admin.properties.RocketMqProperties;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientException;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.apache.rocketmq.client.apis.producer.Producer;
import org.apache.rocketmq.client.apis.producer.SendReceipt;
import org.springframework.beans.factory.InitializingBean;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author mikuyun
 * @since 2025/1/25 14:11
 */
@Slf4j
public class RocketProducer implements InitializingBean {

    private Producer producer;

    private final RocketMqProperties rocketMqProperties;

    public RocketProducer(RocketMqProperties rocketMqProperties) {
        this.rocketMqProperties = rocketMqProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfiguration clientConfig = ClientConfiguration.newBuilder()
                .setEndpoints(rocketMqProperties.getEndpoint())
                .build();
        producer = provider.newProducerBuilder()
                .setClientConfiguration(clientConfig)
                .build();
        log.info("rocketmq producer start, endpoint={}", rocketMqProperties.getEndpoint());
    }

    /**
     * 消息发送
     *
     * @param message 消息
     * @return boolean
     */
    public boolean send(Message message) {
        String content = StandardCharsets.UTF_8.decode(message.getBody()).toString();
        String tag = message.getTag().orElse("");
        try {
            SendReceipt receipt = producer.send(message);
            log.info("rocketmq message topic={} tag={} content={} messageId={}", message.getTopic(), tag, content, receipt.getMessageId());
            return true;
        } catch (ClientException e) {
            log.error("rocketmq message error topic={} tag={} content={}", message.getTopic(), tag, content, e);
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
            for (Message msg : messageList) {
                SendReceipt receipt = producer.send(msg);
                log.info("batch rocketmq message topic={} messageId={}", msg.getTopic(), receipt.getMessageId());
            }
            return true;
        } catch (ClientException e) {
            log.error("batch rocketmq message error", e);
            return false;
        }
    }

    @PreDestroy
    public void destroy() {
        if (producer != null) {
            try {
                producer.close();
                log.info("RocketMQ producer destroyed......");
            } catch (Exception e) {
                log.error("RocketMQ producer close error", e);
            }
        }
    }

}
