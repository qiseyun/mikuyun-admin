package com.mikuyun.admin.evt.sysrole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年5月6日/0006 23点50分
 */
@Data
public class AddSysRoleListEvt {

    /**
     * 角色名称
     */
    @Schema(name = "角色名称")
    @NotBlank
    private String roleName;

    /**
     * 角色代码
     */
    @Schema(name = "角色代码")
    @NotBlank
    private String roleCode;

    /**
     * 角色描述
     */
    @Schema(name = "角色描述")
    @NotBlank
    private String roleDesc;

}
