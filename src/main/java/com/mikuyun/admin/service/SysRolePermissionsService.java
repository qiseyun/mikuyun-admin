package com.mikuyun.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.SysRolePermissions;
import com.mikuyun.admin.dto.syspermissions.EditRPDto;

/**
 * <p>
 * 角色菜单表 服务类
 * </p>
 *
 * @author mikuyun
 * @since 2022-11-05
 */
public interface SysRolePermissionsService extends IService<SysRolePermissions> {

    /**
     * 给角色新增菜单
     *
     * @param dto 参数
     */
    void editRolePermission(EditRPDto dto);

}
