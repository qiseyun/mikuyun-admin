package com.mikuyun.admin.evt.sysuser;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年3月25日/0025 0点17分
 */
@Data
public class AddSysUserEvt {

    @Schema(name = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(name = "用户手机号")
    @NotBlank(message = "手机号不能为空")
    private String telephone;

    @Schema(name = "用户密码")
    @NotBlank(message = "用户密码不能为空")
    private String password;

    @Schema(name = "真实姓名")
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @Schema(name = "邮箱")
    private String email;
}
