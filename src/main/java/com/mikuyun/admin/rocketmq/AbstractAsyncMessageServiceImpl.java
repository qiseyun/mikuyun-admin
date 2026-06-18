package com.mikuyun.admin.rocketmq;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.mikuyun.admin.rocketmq.enums.TopicEnum;
import com.mikuyun.admin.util.MqSerializationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mikuyun
 * @since 2026/3/31 21:28
 */
@Slf4j
public abstract class AbstractAsyncMessageServiceImpl implements IAsyncMessageService {

    protected final RocketProducer rocketProducer;

    protected AbstractAsyncMessageServiceImpl(RocketProducer rocketProducer) {
        this.rocketProducer = rocketProducer;
    }

    @Override
    public boolean rocketMqMessageSend(AsyncMessageDto dto) {
        JSONObject content = contentCheckAndProcess(dto.getContent());
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        org.apache.rocketmq.client.apis.message.MessageBuilder messageBuilder = provider.newMessageBuilder()
                .setTopic(getTopic().getRocketMqTopic())
                .setTag(getTopic().getTag())
                .setKeys(getKey(content))
                .setBody(MqSerializationUtils.serialize(content));
        // 延时消息: 使用 DeliveryTimestamp (rocketmq-client-java 不支持 delayTimeLevel)
        if (dto.getDelayTimeLevel() != null) {
            long deliveryTimestamp = System.currentTimeMillis() + dto.getDelayTimeLevel().toMillis();
            messageBuilder.setDeliveryTimestamp(deliveryTimestamp);
        }
        Message message = messageBuilder.build();
        return rocketProducer.send(message);
    }

    @Override
    public boolean rocketMqMessageSendBatch(AsyncMessageDto dto) {
        JSONObject content = contentCheckAndProcess(dto.getContent());
        List<TopicEnum> topics = getTopics();
        if (CollectionUtil.isEmpty(topics)) {
            return false;
        }
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        List<Message> messages = new ArrayList<>();
        for (TopicEnum topic : topics) {
            Message message = provider.newMessageBuilder()
                    .setTopic(topic.getRocketMqTopic())
                    .setTag(topic.getTag())
                    .setKeys(getKey(content))
                    .setBody(MqSerializationUtils.serialize(content))
                    .build();
            messages.add(message);
        }
        return rocketProducer.sendBatch(messages);
    }

    /**
     * 获取MQ消息key
     * 各实现类根据消息content来制作消息key
     *
     * @param content 消息内容
     * @return String
     */
    public abstract String getKey(JSONObject content);

}
