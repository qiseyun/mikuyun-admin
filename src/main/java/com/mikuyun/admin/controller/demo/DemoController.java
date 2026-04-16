package com.mikuyun.admin.controller.demo;

import cn.hutool.core.util.StrUtil;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.IdNameStrEvt;
import com.mikuyun.admin.evt.ProhibitedWordsCheckEvt;
import com.mikuyun.admin.rocketmq.RocketMqDelayTimeEnum;
import com.mikuyun.admin.rocketmq.RocketProducer;
import com.mikuyun.admin.rocketmq.TopicEnum;
import com.mikuyun.admin.support.LockTemplateSupport;
import com.mikuyun.admin.util.AhoCorasickAutomatonUtils;
import com.mikuyun.admin.util.MqSerializationUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
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
    public R<Void> sendMessage(@RequestBody IdNameStrEvt evt) {
        Message message = new Message();
        message.setKeys("demoId:" + evt.getId());
        message.setBody(MqSerializationUtils.serialize(evt));
        message.setTopic(TopicEnum.TEST.getDesc());
        message.setTags(TopicEnum.TEST.getTag());
        // 延时等级
        message.setDelayTimeLevel(RocketMqDelayTimeEnum.S_10.getLevel());
        rocketProducer.send(message);
        return R.ok();
    }

    @PostMapping("/prohibitedWordsCheck")
    @Operation(summary = "违禁词检测")
    public R<ProhibitedWordsCheckEvt> prohibitedWordsCheck(@RequestBody ProhibitedWordsCheckEvt evt) {
        evt.setToText(ahoCorasickAutomatonUtils.replaceSensitiveWords(evt.getText(), StrUtil.isBlank(evt.getReplacement()) ? "*" : evt.getReplacement()));
        evt.setProhibited(ahoCorasickAutomatonUtils.containsSensitiveWord(evt.getText()));
        evt.setText(null);
        return R.ok(evt);
    }

}
