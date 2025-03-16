package com.mikuyun.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.SysUserRole;
import com.mikuyun.admin.evt.sysrole.AddRoleToSysUser;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author qiseyun
 * @since 2023-05-01
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 给管理员新增角色
     *
     * @param evt id参数
     */
    void addRoleToSysUser(AddRoleToSysUser evt);

}
