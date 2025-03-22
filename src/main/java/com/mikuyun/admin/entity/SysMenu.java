package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单权限表
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mk_sys_menu")
@Schema(description = "菜单权限表")
public class SysMenu extends BaseEntity {

    @Schema(name = "菜单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "菜单名称")
    private String name;

    @Schema(name = "菜单权限标识")
    private String permission;

    @Schema(name = "前端URL")
    private String path;

    @Schema(name = "父菜单ID")
    private Integer parentId;

    @Schema(name = "0-开启，1- 关闭")
    private Integer keepAlive;

    @Schema(name = "菜单类型 （0菜单 1按钮）")
    private Integer type;

}
