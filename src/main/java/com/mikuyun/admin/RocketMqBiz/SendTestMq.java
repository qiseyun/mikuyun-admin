package com.mikuyun.admin.RocketMqBiz;


import com.mikuyun.admin.mqRocket.MqTopicEnum;
import com.mikuyun.admin.evt.IdNameStrEvt;
import com.mikuyun.admin.mqRocket.RocketProducer;
import com.mikuyun.admin.util.MqSerializationUtils;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.common.message.Message;
import org.springframework.stereotype.Component;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/25 14:26
 */
@RequiredArgsConstructor
@Component
public class SendTestMq {

    private final RocketProducer rocketProducer;

    public void sendTestMq(IdNameStrEvt evt) {
        Message message = new Message();
        message.setKeys("userId:" + evt.getId());
        message.setTopic(MqTopicEnum.TEST.getRocketMqTopic());
        message.setTags(MqTopicEnum.TEST.getTag());
        message.setBody(MqSerializationUtils.serialize(evt));
        // 设置属性：自定义延时秒
//        message.setDelayTimeSec(45);
        rocketProducer.send(message);
    }

}
