package com.mikuyun.admin.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.support.LockTemplateSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/3/22 02:04
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/test")
public class TestController {

    private final LockTemplateSupport lockTemplateSupport;

    @SaIgnore
    @PostMapping(value = "lock")
    public R<Void> testLock() {
        lockTemplateSupport.rLock("test", 1, TimeUnit.MINUTES, () -> {
            try {
                TimeUnit.MILLISECONDS.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("testLock");
        });
        return R.ok();
    }

}
