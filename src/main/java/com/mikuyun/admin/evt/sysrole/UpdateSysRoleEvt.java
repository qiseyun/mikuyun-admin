package com.mikuyun.admin.evt.sysrole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2026/2/8 20:37
 */
@Data
public class UpdateSysRoleEvt {

    private Integer id;

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
