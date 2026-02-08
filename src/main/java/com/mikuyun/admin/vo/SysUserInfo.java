package com.mikuyun.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

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
public class SysUserInfo {

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

    /**
     * 权限列表
     */
    private List<String> permissions;

}