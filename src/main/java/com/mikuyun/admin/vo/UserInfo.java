package com.mikuyun.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2022/11/1 10:06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    @Schema(name = "id")
    private Integer id;

    @Schema(name = "账号")
    private String username;

    @Schema(name = "用户手机号(账号)")
    private String telephone;

    @Schema(name = "用户真实姓名(用户则使用昵称)")
    private String realName;

    @Schema(name = "头像")
    private String headPortrait;

    @Schema(name = "邮箱")
    private String email;

    @Schema(name = "管理员类型 (0:超级管理员 1:管理员 3:用户)")
    private Integer userType;

}