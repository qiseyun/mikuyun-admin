package com.mikuyun.admin.rocketmq.producer;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.mikuyun.admin.rocketmq.AbstractAsyncMessageServiceImpl;
import com.mikuyun.admin.rocketmq.AsyncMessageTypeEnum;
import com.mikuyun.admin.rocketmq.RocketProducer;
import com.mikuyun.admin.rocketmq.TopicEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @auth mikuyun
 * @since 2026/4/2 23:18
 */
@Slf4j
@Service
public class DemoAsyncMessageProducer extends AbstractAsyncMessageServiceImpl {

    protected DemoAsyncMessageProducer(RocketProducer rocketProducer) {
        super(rocketProducer);
    }

    @Override
    public String getKey(JSONObject content) {
        return StrUtil.join(":", "bizId", content.getInteger("bizId"));
    }

    @Override
    public AsyncMessageTypeEnum getTypeEnum() {
        return AsyncMessageTypeEnum.DEMO_ONE;
    }

    @Override
    public TopicEnum getTopic() {
        return TopicEnum.TEST;
    }

}
