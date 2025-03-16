package com.mikuyun.admin.evt.mail;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年4月16日/0016 0点55分
 */
@Data
public class MailCaptchaEvt {

    /**
     * 邮箱
     */
    @Schema(name = "邮箱")
    @NotBlank(message = "邮箱不能为空白!")
    private String mail;

}
