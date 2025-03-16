package com.mikuyun.admin.evt.sysrole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2024年1月17日/0017 21点59分
 */
@Data
public class AddRoleToSysUser {

    /**
     * 角色id
     */
    @Schema(name = "角色id")
    @NotNull
    private Integer roleId;

    /**
     * 角色代码
     */
    @Schema(name = "管理员id")
    @NotNull
    private Integer sysUserId;

}
