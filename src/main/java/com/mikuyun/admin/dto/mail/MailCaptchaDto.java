package com.mikuyun.admin.dto.mail;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author mikuyun
 * @since 2023年4月16日/0016 0点55分
 */
@Data
public class MailCaptchaDto {

    /**
     * 邮箱
     */
    @Schema(name = "邮箱")
    @NotBlank(message = "邮箱不能为空白!")
    private String mail;

}
