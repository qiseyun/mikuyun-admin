package com.mikuyun.admin.listener;

import com.alibaba.fastjson2.JSON;
import com.mikuyun.admin.enums.MqTopicEnum;
import com.mikuyun.admin.mqRocket.AbstractRmqMsgListenerEnvIsolation;
import com.mikuyun.admin.util.MqSerializationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/26 10:00
 */
@Slf4j
@Service
public class RocketTestListener extends AbstractRmqMsgListenerEnvIsolation {

    @Override
    protected String getTopic() {
        return MqTopicEnum.TEST.getRocketMqTopic();
    }

    @Override
    public String getTag() {
        return MqTopicEnum.TEST.getTag();
    }

    @Override
    public Boolean consumer(MessageExt message) {
        String deserialize = MqSerializationUtils.deserialize(message.getBody(), String.class);
        // 消费逻辑
        // 四位随机数
        log.info("rocketmq test Consumer param={}", JSON.toJSONString(deserialize));
        return true;
    }

}
