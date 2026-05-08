package com.mikuyun.admin.rocketmq.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.mikuyun.admin.rocketmq.IBaseMessageListener;
import com.mikuyun.admin.rocketmq.enums.TopicEnum;
import com.mikuyun.admin.service.MailService;
import com.mikuyun.admin.util.MqSerializationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

/**
 * @author mikuyun
 * @since 2026/2/20 14:16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginMailConsumer implements IBaseMessageListener {

    private final MailService mailService;

    @Override
    public String getTopic() {
        return TopicEnum.LOGIN_EMAIL.getRocketMqTopic();
    }

    @Override
    public String getTag() {
        return TopicEnum.LOGIN_EMAIL.getTag();
    }

    @Override
    public Boolean consumer(MessageExt message) {
        try {
            JSONObject dto = MqSerializationUtils.deserialize(message.getBody(), JSONObject.class);
            mailService.loginMail(
                    dto.getString("facility"),
                    dto.getString("loginTime"),
                    dto.getString("to"),
                    dto.getString("username")
            );
        } catch (Exception e) {
            log.error("Exception: {} \n", e.getMessage(), e);
            return false;
        }
        return true;
    }
}
