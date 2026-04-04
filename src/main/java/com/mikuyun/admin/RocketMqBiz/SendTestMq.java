package com.mikuyun.admin.RocketMqBiz;


import com.mikuyun.admin.mqRocket.TopicEnum;
import com.mikuyun.admin.evt.IdNameStrEvt;
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

//    private final RocketProducer rocketProducer;

    public void sendTestMq(IdNameStrEvt evt) {
        Message message = new Message();
        message.setKeys("userId:" + evt.getId());
        message.setTopic(TopicEnum.TEST.getRocketMqTopic());
        message.setTags(TopicEnum.TEST.getTag());
        message.setBody(MqSerializationUtils.serialize(evt));
        // 延时等级 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        message.setDelayTimeLevel(3);
//        rocketProducer.send(message);
    }

}
