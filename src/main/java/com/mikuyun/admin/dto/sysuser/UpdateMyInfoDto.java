package com.mikuyun.admin.dto.sysuser;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author mikuyun
 * @since 2026/2/11 22:14
 */
@Data
public class UpdateMyInfoDto {

    @Schema(name = "id")
    @NotNull(message = "id不能为空")
    private Integer id;

    @Schema(name = "头像")
    private String headPortrait;

    @Schema(name = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Schema(name = "密码")
    private String password;

}
