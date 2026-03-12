package com.mikuyun.admin.controller.demo;

import com.mikuyun.admin.RocketMqBiz.SendTestMq;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.IdNameStrEvt;
import com.mikuyun.admin.support.LockTemplateSupport;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/3/22 02:04
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    private final LockTemplateSupport lockTemplateSupport;

    private final SendTestMq sendTestMq;

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
    public R<String> sendMessage(@RequestBody IdNameStrEvt evt) {
        sendTestMq.sendTestMq(evt);
        return R.ok("Message sent successfully!");
    }

}
