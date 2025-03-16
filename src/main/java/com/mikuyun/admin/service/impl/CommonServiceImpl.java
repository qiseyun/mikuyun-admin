package com.mikuyun.admin.service.impl;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import com.mikuyun.admin.common.ResultCode;
import com.mikuyun.admin.entity.Captcha;
import com.mikuyun.admin.enums.CaptchaTypeEnum;
import com.mikuyun.admin.evt.mail.MailCaptchaEvt;
import com.mikuyun.admin.exception.ServiceException;
import com.mikuyun.admin.service.CaptchaService;
import com.mikuyun.admin.service.CommonService;
import com.mikuyun.admin.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年4月16日/0016 0点15分
 */
@Service
@AllArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final MailService mailService;

    private final TemplateEngine templateEngine;

    private final CaptchaService captchaService;

    /**
     * 邮箱正则
     */
    final String MAILBOX_REGULARITY = "^[A-Za-z0-9一-龥]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    @Override
    public void mailCaptcha(MailCaptchaEvt evt) {
        if (!ReUtil.isMatch(MAILBOX_REGULARITY, evt.getMail())) {
            throw new ServiceException(ResultCode.PARAM_ERROR, "请输入正确格式的邮箱!");
        }
        Captcha cap = new Captcha();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        LocalDateTime expirationDateStart = LocalDateTime.now();
        LocalDateTime expirationDateEnd = expirationDateStart.plusMinutes(10);
        cap.setGmtCreated(expirationDateStart);
        cap.setExpirationTime(expirationDateEnd);
        cap.setAccount(evt.getMail());
        // 六位验证码
        int captcha = RandomUtil.randomInt(100000, 999999);
        cap.setCaptchaStr(String.valueOf(captcha));
        cap.setCaptchaType(CaptchaTypeEnum.MAIL.getCaptchaType());
        // 设置邮件内容
        Context context = new Context();
        String subject = "登录验证码";
        // 主题
        context.setVariable("captcha", captcha);
        context.setVariable("expirationDateStart", expirationDateStart.format(pattern));
        context.setVariable("expirationDateEnd", expirationDateEnd.format(pattern));
        String mail = templateEngine.process("mailCaptcha.html", context);
        mailService.sendHtmlMail(evt.getMail(), subject, mail);
        captchaService.save(cap);
    }

}
