package com.mikuyun.admin.evt.sysmenu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2024年1月17日/0017 22点09分
 */
@Data
public class AddMenuToRoleEvt {

    /**
     * 角色id
     */
    @Schema(name = "角色id")
    @NotNull
    private Integer roleId;

    /**
     * 菜单id
     */
    @Schema(name = "菜单id")
    @NotNull
    private Integer menuId;

}
