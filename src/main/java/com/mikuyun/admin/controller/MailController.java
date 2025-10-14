package com.mikuyun.admin.controller;


import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.mail.MailCaptchaEvt;
import com.mikuyun.admin.service.CommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年4月16日/0016 0点02分
 */
@Tag(name = "邮件相关接口")
@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final CommonService commonService;

    /**
     * 邮件验证码
     */
    @PostMapping(value = "/captcha")
    @Operation(summary = "邮件验证码")
    public R<Void> mailCaptcha(@RequestBody MailCaptchaEvt evt) {
        commonService.mailCaptcha(evt);
        return R.ok();
    }

}
