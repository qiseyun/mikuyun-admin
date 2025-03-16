package com.mikuyun.admin.evt;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2022/11/1 15:35
 */
@Data
public class LoginEvt {

    /**
     * 用户名
     */
    @Schema(name = "用户名")
    @NotBlank(message = "用户名不能为空白!")
    private String username;

    /**
     * 密码
     */
    @Schema(name = "密码")
    @NotBlank(message = "密码不能为空白!")
    private String password;

}
