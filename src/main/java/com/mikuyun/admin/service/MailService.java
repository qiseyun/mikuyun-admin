package com.mikuyun.admin.service;

import java.io.File;
import java.time.LocalDateTime;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2023/4/3 11:08
 */
public interface MailService {

    /**
     * 发送简单邮件
     *
     * @param to      收件人
     * @param cc      抄送人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMall(String to, String cc, String subject, String content);

    /**
     * 发送带附件的邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     * @param file    文件
     */
    void sendAttachFileMail(String to, String subject, String content, File file);

    /**
     * 发送HTML格式的邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 登录邮件
     *
     * @param facility    登录设备
     * @param loginTime 登陆时间
     * @param to          收件人
     * @param username    用户名
     */
    void loginMail(String facility, String loginTime, String to, String username);

}
