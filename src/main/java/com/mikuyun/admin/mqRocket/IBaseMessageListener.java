package com.mikuyun.admin.mqRocket;

import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/25 14:18
 */
public interface IBaseMessageListener {

    /**
     * 获取topic
     *
     * @return topic
     */
    String getTopicEnv();

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
    Boolean consumer(MessageExt message);

}
