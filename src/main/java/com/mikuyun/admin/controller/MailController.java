package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaIgnore;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.dto.mail.MailCaptchaDto;
import com.mikuyun.admin.service.CommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mikuyun
 * @since 2023年4月16日/0016 0点02分
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
    @SaIgnore
    @PostMapping(value = "/captcha")
    @Operation(summary = "邮件验证码")
    public R<Void> mailCaptcha(@RequestBody MailCaptchaDto dto) {
        commonService.mailCaptcha(dto);
        return R.ok();
    }

}
