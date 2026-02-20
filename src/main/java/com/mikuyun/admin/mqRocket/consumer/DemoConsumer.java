package com.mikuyun.admin.mqRocket.consumer;

import com.alibaba.fastjson2.JSON;
import com.mikuyun.admin.evt.IdNameStrEvt;
import com.mikuyun.admin.mqRocket.IBaseMessageListener;
import com.mikuyun.admin.mqRocket.MqTopicEnum;
import com.mikuyun.admin.util.MqSerializationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2026/2/20 14:16
 */
@Slf4j
@Service
public class DemoConsumer implements IBaseMessageListener {

    @Override
    public String getTopic() {
        return MqTopicEnum.TEST.getRocketMqTopic();
    }

    @Override
    public String getTag() {
        return MqTopicEnum.TEST.getTag();
    }

    @Override
    public Boolean consumer(MessageExt message) {
        try {
            IdNameStrEvt evt = MqSerializationUtils.deserialize(message.getBody(), IdNameStrEvt.class);
            log.info(JSON.toJSONString(evt));
        } catch (Exception e) {
            log.error("Exception: {} \n", e.getMessage(), e);
            return false;
        }
        return true;
    }
}
