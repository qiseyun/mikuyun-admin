package com.mikuyun.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.SysRole;
import com.mikuyun.admin.evt.sysrole.AddSysRoleListEvt;
import com.mikuyun.admin.vo.sysrole.QuerySysRoleListVo;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-05
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 查询管理员角色信息
     *
     * @param sysUserId 管理员id
     * @return 角色信息
     */
    List<SysRole> querySysRoleInfo(Object sysUserId);

    /**
     * 查询账号角色集合
     *
     * @param sysUserId 管理员id
     * @return 账号角色集合
     */
    List<String> querySysRole(Object sysUserId);

    /**
     * 系统角色列表查询
     *
     * @return List<QuerySysRoleListVo>
     */
    List<QuerySysRoleListVo> queryRoleList();

    /**
     * 新增菜单或按钮
     *
     * @param evt 新增参数
     */
    void addSysRole(AddSysRoleListEvt evt);

}
