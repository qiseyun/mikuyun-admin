package com.mikuyun.admin.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.alibaba.fastjson2.JSON;
import com.mikuyun.admin.annotation.SecurityVerification;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.rocketmq.AsyncMessageDto;
import com.mikuyun.admin.rocketmq.IAsyncMessageService;
import com.mikuyun.admin.factory.AsyncMessageFactory;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mikuyun
 * @since 2026/4/2 22:41
 */
@Slf4j
@RequestMapping(value = "/async/message")
@RestController
@RequiredArgsConstructor
public class AsyncMessageController {

    private final AsyncMessageFactory asyncMessageFactory;

    @Operation(summary = "发送消息到消息队列(用于消息补发)")
    @PostMapping(value = "/send")
    @SaIgnore
    @SecurityVerification
    public R<Boolean> send(@Valid @RequestBody AsyncMessageDto dto) {
        IAsyncMessageService asyncMessageService = asyncMessageFactory.getAsyncMessageService(dto.getType());
        boolean result = asyncMessageService.isBatch() ? asyncMessageService.rocketMqMessageSendBatch(dto) : asyncMessageService.rocketMqMessageSend(dto);
        log.info("asyncMessageSend params={} isBatch={} result={}", JSON.toJSONString(dto), asyncMessageService.isBatch(), result);
        return R.ok(result);
    }

}
