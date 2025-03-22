package com.mikuyun.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.SysMenu;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.evt.sysmenu.AddMenuOrButtonEvt;
import com.mikuyun.admin.vo.sysmenu.SysMenuListVo;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-05
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 用户角色菜单权限列表
     *
     * @param sysUserId 用户id
     * @return 对应角色权限列表
     */
    List<String> sysRoleMenuPermissions(Object sysUserId);

    /**
     * 查询菜单列表
     *
     * @param evt 查询参数
     * @return 菜单列表
     */
    List<SysMenuListVo> queryMenuList(IdEvt evt);

    /**
     * 新增菜单或按钮
     *
     * @param evt 新增参数
     */
    void addMenuOrButton(AddMenuOrButtonEvt evt);

    /**
     * 管理员菜单树
     *
     * @return 菜单树
     */
    List<SysMenuListVo> getMenuTree();

}
