package com.mikuyun.admin.rocketmq;

import com.alibaba.fastjson2.JSONObject;

import java.util.List;

/**
 * @auth mikuyun
 * @date 2026/3/31 21:26
 */
public interface IAsyncMessageService {

    /**
     * 获取消息业务类型
     * 每种类型会对应一个或多个topic,根据getTopic和getTopics方法获取需要发送的topic
     *
     * @return AsyncMessageTypeEnum
     */
    AsyncMessageTypeEnum getTypeEnum();

    /**
     * 单个消息发送,支持延时等级
     *
     * @return boolean
     */
    boolean rocketMqMessageSend(AsyncMessageEvt evt);

    /**
     * 批量消息发送,不支持延时等级,因为这是要发送到多个topic的
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

    /**
     * 获取要广播的topic
     *
     * @return TopicEnum
     */
    default List<TopicEnum> getTopics() {
        return null;
    }

    /**
     * 是否广播
     *
     * @return boolean
     */
    default boolean isBatch() {
        return false;
    }

    /**
     * content校验和处理
     *
     * @param content 消息内容
     * @return JSONObject
     */
    default JSONObject contentCheckAndProcess(JSONObject content) {
        return content;
    }

}
