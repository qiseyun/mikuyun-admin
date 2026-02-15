package com.mikuyun.admin.evt.sysmenu;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年5月1日/0001 17点06分
 */
@Data
public class AddOrEditMenuOrButtonEvt {

    private Integer id;

    /**
     * 菜单名称
     */
    @Schema(name = "菜单名称")
    @NotBlank
    private String name;

    /**
     * 菜单权限标识
     */
    @Schema(name = "菜单权限标识")
    @NotBlank
    private String permission;

    /**
     * 父菜单ID
     */
    @Schema(name = "父菜单ID(是父菜单就填0)")
    @NotNull
    private Integer parentId;

    @Schema(name = "0-开启，1- 关闭")
    @NotNull
    private Integer keepAlive;

    @Schema(name = "菜单类型 （-1根节点 0菜单 1按钮）")
    @NotNull
    private Integer type;

    @Schema(name = "描述")
    private String describe;

}
