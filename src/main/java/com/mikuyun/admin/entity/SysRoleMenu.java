package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色菜单表
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("qise_sys_role_menu")
@Schema(description = "角色菜单表")
public class SysRoleMenu {


    @Schema(name = "角色ID")
    private Integer roleId;

    @Schema(name = "菜单ID")
    private Integer menuId;


}
