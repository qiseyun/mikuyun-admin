package com.mikuyun.admin.vo.syspermissions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年5月1日/0001 1点20分
 */
@Data
public class QueryPermissionListVo {

    /**
     * 菜单id
     */
    private Integer id;

    /**
     * 菜单名称
     */
    @Schema(name = "菜单名称")
    private String name;

    /**
     * 菜单权限标识
     */
    @Schema(name = "菜单权限标识")
    private String permission;

    /**
     * 父菜单ID
     */
    @Schema(name = "父菜单ID")
    private Integer pid;

}
