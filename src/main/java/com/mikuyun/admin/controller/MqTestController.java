package com.mikuyun.admin.controller;


import com.mikuyun.admin.RocketMqBiz.SendTestMq;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.IdNameStrEvt;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/25 11:05
 */
@RequiredArgsConstructor
@RestController
public class MqTestController {

    private final SendTestMq sendTestMq;

    @PostMapping("/send")
    public R<String> sendMessage(@RequestBody IdNameStrEvt evt) {
        sendTestMq.sendTestMq(evt);
        return R.ok( "Message sent successfully!");
    }

}
