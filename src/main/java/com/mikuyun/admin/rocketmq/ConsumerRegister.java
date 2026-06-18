package com.mikuyun.admin.rocketmq;

import cn.hutool.core.collection.CollectionUtil;
import com.mikuyun.admin.properties.RocketMqProperties;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientConfiguration;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.consumer.FilterExpression;
import org.apache.rocketmq.client.apis.consumer.PushConsumer;
import org.apache.rocketmq.client.apis.consumer.PushConsumerBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mikuyun
 * @since 2025/1/25 14:20
 */
@Slf4j
@RequiredArgsConstructor
public class ConsumerRegister implements InitializingBean {

    private final RocketMqProperties rocketMqProperties;

    private final ApplicationContext applicationContext;

    @Getter
    private final List<PushConsumer> consumerList = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, IBaseMessageListener> beansOfType = applicationContext.getBeansOfType(IBaseMessageListener.class);
        if (CollectionUtil.isEmpty(beansOfType)) {
            return;
        }
        List<String> topicList = new ArrayList<>();
        Map<String, List<IBaseMessageListener>> topicMap = beansOfType.values().stream().collect(Collectors.groupingBy(IBaseMessageListener::getTopic));

        ClientServiceProvider provider = ClientServiceProvider.loadService();
        ClientConfiguration clientConfig = ClientConfiguration.newBuilder()
                .setEndpoints(rocketMqProperties.getEndpoint())
                .build();

        // 构建订阅表达式: topic → FilterExpression.SUB_ALL (订阅所有tag)
        Map<String, FilterExpression> subscriptionExpressions = new HashMap<>();
        for (String topic : topicMap.keySet()) {
            subscriptionExpressions.put(topic, FilterExpression.SUB_ALL);
            topicList.add(topic);
        }

        PushConsumerBuilder builder = provider.newPushConsumerBuilder()
                .setClientConfiguration(clientConfig)
                .setConsumerGroup(rocketMqProperties.getGroupName())
                .setSubscriptionExpressions(subscriptionExpressions)
                .setMessageListener(new TopicMessageListenerWrapper(topicMap));
        // 设置消费线程数
        if (rocketMqProperties.getConsumeThread() != null) {
            builder.setConsumptionThreadCount(rocketMqProperties.getConsumeThread());
        }
        try {
            PushConsumer pushConsumer = builder.build();
            log.info("subscribed topics={} consumeThreadNum={} start", topicList, rocketMqProperties.getConsumeThread());
            consumerList.add(pushConsumer);
        } catch (Exception e) {
            log.error("RocketMQ consumer failed to connect to proxy endpoint={}, consumer will not be available. Error: {}",
                    rocketMqProperties.getEndpoint(), e.getMessage(), e);
        }
    }

    @PreDestroy
    public void destroy() {
        if (CollectionUtil.isNotEmpty(consumerList)) {
            log.info("Shutting down RocketMQ consumers for group: {}", rocketMqProperties.getGroupName());
            for (PushConsumer consumer : consumerList) {
                try {
                    consumer.close();
                    log.info("RocketMQ consumer shutdown completed for group: {}", rocketMqProperties.getGroupName());
                } catch (Exception e) {
                    log.error("RocketMQ consumer close error", e);
                }
            }
        }
    }

}
