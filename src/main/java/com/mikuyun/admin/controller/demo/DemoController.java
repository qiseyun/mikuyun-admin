package com.mikuyun.admin.controller.demo;

import cn.hutool.core.util.StrUtil;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.dto.IdNameStrDto;
import com.mikuyun.admin.dto.ProhibitedWordsCheckDto;
import com.mikuyun.admin.rocketmq.RocketProducer;
import com.mikuyun.admin.rocketmq.enums.RocketMqDelayTimeEnum;
import com.mikuyun.admin.rocketmq.enums.TopicEnum;
import com.mikuyun.admin.support.LockTemplateSupport;
import com.mikuyun.admin.util.AhoCorasickAutomatonUtils;
import com.mikuyun.admin.util.MqSerializationUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.apis.ClientServiceProvider;
import org.apache.rocketmq.client.apis.message.Message;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author mikuyun
 * @since 2025/3/22 02:04
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    private final LockTemplateSupport lockTemplateSupport;

    private final AhoCorasickAutomatonUtils ahoCorasickAutomatonUtils;

    private final RocketProducer rocketProducer;

    @PostMapping(value = "lock")
    @Operation(summary = "redis锁模板")
    public R<Void> testLock() {
        lockTemplateSupport.rLock("lock_test", 1L, TimeUnit.MINUTES, () -> {
            try {
                log.info("testLock");
                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        return R.ok();
    }

    @PostMapping("/send")
    @Operation(summary = "rocketmq使用demo,需要开启rocketmq配置")
    public R<Void> sendMessage(@RequestBody IdNameStrDto dto) {
        ClientServiceProvider provider = ClientServiceProvider.loadService();
        Message message = provider.newMessageBuilder()
                .setKeys("demoId:" + dto.getId())
                .setBody(MqSerializationUtils.serialize(dto))
                .setTopic(TopicEnum.TEST.getRocketMqTopic())
                .setTag(TopicEnum.TEST.getTag())
                // 如需延时消息, 需要先将 topic 的消息类型设置为 DELAY (或混合类型)
                 .setDeliveryTimestamp(System.currentTimeMillis() + RocketMqDelayTimeEnum.S_10.toMillis())
                .build();
        rocketProducer.send(message);
        return R.ok();
    }

    @PostMapping("/prohibitedWordsCheck")
    @Operation(summary = "违禁词检测")
    public R<ProhibitedWordsCheckDto> prohibitedWordsCheck(@RequestBody ProhibitedWordsCheckDto dto) {
        dto.setToText(ahoCorasickAutomatonUtils.replaceSensitiveWords(dto.getText(), StrUtil.isBlank(dto.getReplacement()) ? "*" : dto.getReplacement()));
        dto.setProhibited(ahoCorasickAutomatonUtils.containsSensitiveWord(dto.getText()));
        dto.setText(null);
        return R.ok(dto);
    }

}
