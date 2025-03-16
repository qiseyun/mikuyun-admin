package com.mikuyun.admin.service.impl;


import cn.hutool.core.util.StrUtil;
import com.mikuyun.admin.service.AsyncService;
import com.mikuyun.admin.service.MailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年3月25日/0025 0点17分
 */
@Slf4j
@Service
@AllArgsConstructor
public class AsyncServiceImpl implements AsyncService {

    private final MailService mailService;

    private final TemplateEngine templateEngine;

    @Async(value = "asyncTaskExecutor")
    @Override
    public void loginMail(String facility, LocalDateTime landingTime, String to, String username) {
        if (StrUtil.isBlank(to)) {
            return;
        }
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        // 设置邮件内容
        Context context = new Context();
        String subject = "用户登录提醒";
        String content = "详情：" + "您的账号已登录设备[" + facility + "]。";
        String date = "登陆时间：" + landingTime.format(pattern);
        context.setVariable("content", content);
        context.setVariable("date", date);
        context.setVariable("user", username);
        String mail = templateEngine.process("loginInformMailTemplate.html", context);
        mailService.sendHtmlMail(to, subject, mail);
    }

}
