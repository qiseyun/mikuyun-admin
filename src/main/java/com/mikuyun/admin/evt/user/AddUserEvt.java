package com.mikuyun.admin.evt.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2022/11/3 11:17
 */
@Data
public class AddUserEvt {


    /**
     * 手机号
     */
    @Schema(name = "用户手机号")
    @NotBlank(message = "手机号不能为空")
    private String telephone;

    /**
     * 省
     */
    @Schema(name = "省")
    private Integer provinceId;

    /**
     * 用户密码
     */
    @Schema(name = "用户密码")
    @NotBlank(message = "用户密码不能为空")
    private String password;

}
