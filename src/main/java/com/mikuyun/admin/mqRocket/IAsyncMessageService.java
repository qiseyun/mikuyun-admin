package com.mikuyun.admin.mqRocket;

import com.alibaba.fastjson2.JSONObject;

import java.util.List;

/**
 * @auth mikuyun
 * @date 2026/3/31 21:26
 */
public interface IAsyncMessageService {

    /**
     * 获取消息业务类型
     *
     * @return AsyncMessageTypeEnum
     */
    AsyncMessageTypeEnum getTypeEnum();

    /**
     * 单个消息发送
     *
     * @return boolean
     */
    boolean rocketMqMessageSend(AsyncMessageEvt evt);

    /**
     * 批量消息发送
     *
     * @return boolean
     */
    boolean rocketMqMessageSendBatch(AsyncMessageEvt evt);

    /**
     * 获取要发送的topic
     *
     * @return TopicEnum
     */
    TopicEnum getTopic();

    default List<TopicEnum> getTopics() {
        return null;
    }

    /**
     * 是否广播topic
     *
     * @return boolean
     */
    default boolean isBatch() {
        return false;
    }

    default JSONObject contentCheckAndProcess(JSONObject content) {
        return content;
    }

}
