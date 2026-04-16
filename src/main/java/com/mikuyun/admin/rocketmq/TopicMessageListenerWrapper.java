package com.mikuyun.admin.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mikuyun
 * @since 2025/1/25 14:17
 */
@Slf4j
public class TopicMessageListenerWrapper implements MessageListenerConcurrently {

    private final Map<String, List<IBaseMessageListener>> topicMap;

    public TopicMessageListenerWrapper(Map<String, List<IBaseMessageListener>> topicMap) {
        this.topicMap = topicMap;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgList, ConsumeConcurrentlyContext context) {
        MessageExt messageExt = msgList.getFirst();
        String tags = messageExt.getTags();
        String topic = messageExt.getTopic();
        String content = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        List<IBaseMessageListener> messageListeners = topicMap.get(topic);
        Map<String, IBaseMessageListener> tagListenerMap = messageListeners.stream().collect(Collectors.toMap(IBaseMessageListener::getTag, v -> v));
        IBaseMessageListener messageListener = tagListenerMap.get(tags);
        try {
            if (messageListener != null) {
                boolean result = messageListener.consumer(messageExt);
                log.info("consume message topic={} tag={} content={} result={}", topic, tags, content, result);
                return result ? ConsumeConcurrentlyStatus.CONSUME_SUCCESS : ConsumeConcurrentlyStatus.RECONSUME_LATER;
            } else {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        } catch (Exception e) {
            log.error("consume error topic={}, tag={}, reconsumeTimes: {}, content={}, errorMsg={}", topic, tags, messageExt.getReconsumeTimes(), content, e.getMessage());
            // 这里只是会进行默认配置的重新消费, 如果都失败了就会直接丢弃, 需要到rocketmq打开死信队列
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }

}
