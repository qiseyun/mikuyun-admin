package com.mikuyun.admin.rocketmq;

import org.apache.rocketmq.client.apis.message.MessageView;

/**
 * @author mikuyun
 * @since 2025/1/25 14:18
 */
public interface IBaseMessageListener {

    /**
     * 获取topic
     *
     * @return topic
     */
    String getTopic();

    /**
     * 获取tag
     *
     * @return tag
     */
    String getTag();

    /**
     * 消费消息
     *
     * @param message 消息
     * @return boolean
     */
    Boolean consumer(MessageView message);

}
