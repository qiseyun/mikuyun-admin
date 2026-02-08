package com.mikuyun.admin.evt.sysuser;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2026/2/8 16:26
 */
@Data
public class UpdateSysUserEvt {

    @NotNull(message = "id不能为空")
    private Integer id;

    @Schema(name = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(name = "用户手机号")
    @NotBlank(message = "手机号不能为空")
    private String telephone;

    @Schema(name = "真实姓名")
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @Schema(name = "邮箱")
    private String email;

}
