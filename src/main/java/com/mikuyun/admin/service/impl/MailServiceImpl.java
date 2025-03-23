package com.mikuyun.admin.service.impl;


import com.mikuyun.admin.common.ResultCode;
import com.mikuyun.admin.properties.WebConfigProperties;
import com.mikuyun.admin.exception.ServiceException;
import com.mikuyun.admin.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2023/4/3 11:09
 */
@Slf4j
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private final WebConfigProperties webConfigProperties;

    private final JavaMailSender mailSender;

    /**
     * 发信邮箱
     */
    private String getEmail() {
        return webConfigProperties.getMallNickname() + "<" + webConfigProperties.getMallAccount() + ">";
    }

    @Override
    public void sendSimpleMall(String to, String cc, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(getEmail());
            message.setTo(to);
            message.setCc(cc);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.MAIL_SEND_ERROR);
        }
    }

    @Override
    public void sendAttachFileMail(String to, String subject, String content, File file) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(getEmail());
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容
            messageHelper.setText(content);
            //添加附件
            messageHelper.addAttachment(file.getName(), file);
            //发送
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new ServiceException(ResultCode.MAIL_SEND_ERROR);
        }
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            //邮件发送人
            messageHelper.setFrom(getEmail());
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);
            log.info("登陆通知邮件已发送至：{}", to);
        } catch (MessagingException e) {
            throw new ServiceException(ResultCode.MAIL_SEND_ERROR);
        }
    }

}