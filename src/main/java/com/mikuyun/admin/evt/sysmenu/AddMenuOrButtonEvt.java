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
public class AddMenuOrButtonEvt {

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

    /**
     * 前端URL
     */
    @Schema(name = "前端URL")
    @NotBlank
    private String path;

    /**
     * 父菜单ID
     */
    @Schema(name = "图标")
    private String icon;

    /**
     * vue页面
     */
    @Schema(name = "vue页面")
    @NotBlank
    private String component;

    @Schema(name = "排序值")
    private Integer sort;

    @Schema(name = "0-开启，1- 关闭")
    @NotNull
    private Integer keepAlive;

    @Schema(name = "菜单类型 （0菜单 1按钮）")
    @NotNull
    private Integer type;

}
