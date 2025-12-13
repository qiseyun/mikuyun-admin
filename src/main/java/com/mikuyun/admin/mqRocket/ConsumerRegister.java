package com.mikuyun.admin.mqRocket;

import cn.hutool.core.collection.CollectionUtil;
import com.mikuyun.admin.properties.RocketMqProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/25 14:20
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ConsumerRegister implements InitializingBean {

    private final RocketMqProperties rocketMqProperties;

    private final ApplicationContext applicationContext;

    @Getter
    private final List<DefaultMQPushConsumer> consumerList = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, IBaseMessageListener> beansOfType = applicationContext.getBeansOfType(IBaseMessageListener.class);
        if (CollectionUtil.isEmpty(beansOfType)) {
            return;
        }
        List<String> topicList = new ArrayList<>();
        Map<String, List<IBaseMessageListener>> topicMap = beansOfType.values().stream().collect(Collectors.groupingBy(IBaseMessageListener::getTopic));
        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer(rocketMqProperties.getGroupName());
        pushConsumer.setConsumerGroup(rocketMqProperties.getGroupName());
        // 设置NameServer的地址
        pushConsumer.setNamesrvAddr(rocketMqProperties.getNameServer());
        for (Map.Entry<String, List<IBaseMessageListener>> topic : topicMap.entrySet()) {
            pushConsumer.subscribe(topic.getKey(), "*");
            topicList.add(topic.getKey());
        }
        pushConsumer.registerMessageListener(new TopicMessageListenerWrapper(topicMap));
        pushConsumer.setConsumeThreadMin(rocketMqProperties.getConsumeThread());
        pushConsumer.setConsumeThreadMax(rocketMqProperties.getConsumeThread());
        pushConsumer.start();
        log.info("subscribed topics={} consumeThreadNum={} start", topicList, rocketMqProperties.getConsumeThread());
        consumerList.add(pushConsumer);
    }

}
