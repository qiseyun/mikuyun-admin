package com.mikuyun.admin.config;

import cn.dev33.satoken.stp.StpInterface;
import com.mikuyun.admin.service.SysMenuService;
import com.mikuyun.admin.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2022/11/1 14:30
 */
@Slf4j
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysMenuService sysMenuService;

    /**
     * 权限
     *
     * @return 返回账号权限集合
     */
    @Override
    public List<String> getPermissionList(Object o, String s) {
        // 权限分菜单权限和按钮权限
        return sysMenuService.sysRoleMenuPermissions(o);
    }

    /**
     * 角色
     *
     * @return 返回账号角色集合
     */
    @Override
    public List<String> getRoleList(Object o, String s) {
        // 管理员角色查询
        return sysRoleService.querySysRole(o);
    }
}
