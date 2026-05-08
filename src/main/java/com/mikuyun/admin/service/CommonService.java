package com.mikuyun.admin.service;


import com.mikuyun.admin.dto.mail.MailCaptchaDto;

/**
 * @author mikuyun
 * @since 2023年4月16日/0016 0点14分
 */
public interface CommonService {

    void mailCaptcha(MailCaptchaDto dto);

}
