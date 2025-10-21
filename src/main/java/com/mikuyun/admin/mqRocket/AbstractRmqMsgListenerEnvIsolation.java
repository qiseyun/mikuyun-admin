package com.mikuyun.admin.mqRocket;

import com.mikuyun.admin.enums.MqTopicEnum;
import com.mikuyun.admin.properties.RocketMqProperties;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/28 11:55
 */
@RequiredArgsConstructor
public abstract class AbstractRmqMsgListenerEnvIsolation implements IBaseMessageListener {

    private final RocketMqProperties rocketMqProperties;

    protected abstract String getTopic();

    @Override
    public String getTopicEnv() {
        // topic环境隔离
        if (rocketMqProperties.getEnvironmentalIsolation()) {
            return MqTopicEnum.TEST.getRocketMqTopic() + "_" + rocketMqProperties.getTopicPrefix();
        }
        return getTopic();
    }

    @Override
    public abstract String getTag();

    @Override
    public abstract Boolean consumer(MessageExt message);

}
