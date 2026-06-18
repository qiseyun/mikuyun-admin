package com.mikuyun.admin.rocketmq.consumer;

import com.alibaba.fastjson2.JSON;
import com.mikuyun.admin.dto.IdNameStrDto;
import com.mikuyun.admin.rocketmq.IBaseMessageListener;
import com.mikuyun.admin.rocketmq.enums.TopicEnum;
import com.mikuyun.admin.util.MqSerializationUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.message.MessageView;
import org.springframework.stereotype.Service;

/**
 * @author mikuyun
 * @since 2026/2/20 14:16
 */
@Slf4j
@Service
public class DemoConsumer implements IBaseMessageListener {

    @Override
    public String getTopic() {
        return TopicEnum.TEST.getRocketMqTopic();
    }

    @Override
    public String getTag() {
        return TopicEnum.TEST.getTag();
    }

    @Override
    public Boolean consumer(MessageView message) {
        try {
            IdNameStrDto dto = MqSerializationUtils.deserialize(message.getBody(), IdNameStrDto.class);
            log.info(JSON.toJSONString(dto));
        } catch (Exception e) {
            log.error("Exception: {} \n", e.getMessage(), e);
            return false;
        }
        return true;
    }
}
