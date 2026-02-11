package com.mikuyun.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.SysUserRole;
import com.mikuyun.admin.evt.sysuser.EditUserRoleEvt;

import java.util.List;

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
     * 获取后台用户角色
     *
     * @param sysUserId 后台用户id
     * @return List<Integer>
     */
    List<Integer> getRoles(Integer sysUserId);

    /**
     * 编辑后台用户角色
     *
     * @param evt 编辑参数
     */
    void editRoles(EditUserRoleEvt evt);

}
