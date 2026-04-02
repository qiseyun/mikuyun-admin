package com.mikuyun.admin.mqRocket;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSONObject;
import com.mikuyun.admin.util.MqSerializationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @auth mikuyun
 * @date 2026/3/31 21:28
 */
@Slf4j
public abstract class AbstractAsyncMessageServiceImpl implements IAsyncMessageService {

    protected final RocketProducer rocketProducer;

    protected AbstractAsyncMessageServiceImpl(RocketProducer rocketProducer) {
        this.rocketProducer = rocketProducer;
    }

    @Override
    public boolean rocketMqMessageSend(AsyncMessageEvt evt) {
        JSONObject content = contentCheckAndProcess(evt.getContent());
        Message message = new Message();
        message.setTopic(getTopic().getRocketMqTopic());
        message.setTags(getTopic().getTag());
        message.setKeys(getKey(content));
        message.setBody(MqSerializationUtils.serialize(content));
        //设置延迟时间
        if (evt.getDelayTimeSec() != null && evt.getDelayTimeSec() > 0) {
            message.setDelayTimeSec(evt.getDelayTimeSec());
        }
        return rocketProducer.send(message);
    }

    @Override
    public boolean rocketMqMessageSendBatch(AsyncMessageEvt evt) {
        JSONObject content = contentCheckAndProcess(evt.getContent());
        List<TopicEnum> topics = getTopics();
        if (CollectionUtil.isEmpty(topics)) {
            return false;
        }
        List<Message> messages = new ArrayList<>();
        for (TopicEnum topic : topics) {
            Message message = new Message();
            message.setTopic(topic.getRocketMqTopic());
            message.setTags(topic.getTag());
            message.setKeys(getKey(content));
            message.setBody(MqSerializationUtils.serialize(content));
            messages.add(message);
        }
        return rocketProducer.sendBatch(messages);
    }

    /**
     * 获取MQ消息key
     *
     * @param content 消息内容
     * @return String
     */
    public abstract String getKey(JSONObject content);

}
