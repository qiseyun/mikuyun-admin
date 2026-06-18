package com.mikuyun.admin.rocketmq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.consumer.ConsumeResult;
import org.apache.rocketmq.client.apis.consumer.MessageListener;
import org.apache.rocketmq.client.apis.message.MessageView;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mikuyun
 * @since 2025/1/25 14:17
 */
@Slf4j
public class TopicMessageListenerWrapper implements MessageListener {

    private final Map<String, List<IBaseMessageListener>> topicMap;

    public TopicMessageListenerWrapper(Map<String, List<IBaseMessageListener>> topicMap) {
        this.topicMap = topicMap;
    }

    @Override
    public ConsumeResult consume(MessageView messageView) {
        String tags = messageView.getTag().orElse("");
        String topic = messageView.getTopic();
        String content = StandardCharsets.UTF_8.decode(messageView.getBody()).toString();
        List<IBaseMessageListener> messageListeners = topicMap.get(topic);
        Map<String, IBaseMessageListener> tagListenerMap = messageListeners.stream().collect(Collectors.toMap(IBaseMessageListener::getTag, v -> v));
        IBaseMessageListener messageListener = tagListenerMap.get(tags);
        try {
            if (messageListener != null) {
                boolean result = messageListener.consumer(messageView);
                log.info("consume message topic={} tag={} content={} result={}", topic, tags, content, result);
                return result ? ConsumeResult.SUCCESS : ConsumeResult.FAILURE;
            } else {
                return ConsumeResult.SUCCESS;
            }
        } catch (Exception e) {
            log.error("consume error topic={}, tag={}, content={}, errorMsg={}", topic, tags, content, e.getMessage());
            // 返回 FAILURE 会触发重试，重试次数耗尽后进入死信队列
            return ConsumeResult.FAILURE;
        }
    }

}
