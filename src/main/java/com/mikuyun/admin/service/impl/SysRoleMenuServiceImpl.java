package com.mikuyun.admin.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.SysRoleMenu;
import com.mikuyun.admin.evt.sysmenu.AddMenuToRoleEvt;
import com.mikuyun.admin.mapper.SysRoleMenuMapper;
import com.mikuyun.admin.service.SysRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-05
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    public void addMenuToRole(AddMenuToRoleEvt evt) {
        SysRoleMenu sysRoleMenu = new SysRoleMenu();
        BeanUtil.copyProperties(evt, sysRoleMenu);
        this.save(sysRoleMenu);
    }

}
