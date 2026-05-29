package com.mikuyun.admin.controller.demo;

import cn.hutool.core.util.StrUtil;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.dto.ProhibitedWordsCheckDto;
import com.mikuyun.admin.support.LockTemplateSupport;
import com.mikuyun.admin.util.AhoCorasickAutomatonUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping("/prohibitedWordsCheck")
    @Operation(summary = "违禁词检测")
    public R<ProhibitedWordsCheckDto> prohibitedWordsCheck(@RequestBody ProhibitedWordsCheckDto dto) {
        dto.setToText(ahoCorasickAutomatonUtils.replaceSensitiveWords(dto.getText(), StrUtil.isBlank(dto.getReplacement()) ? "*" : dto.getReplacement()));
        dto.setProhibited(ahoCorasickAutomatonUtils.containsSensitiveWord(dto.getText()));
        dto.setText(null);
        return R.ok(dto);
    }

}
