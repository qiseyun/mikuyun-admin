package com.mikuyun.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.SysRoleMenu;
import com.mikuyun.admin.evt.sysmenu.EditRPEvt;
import com.mikuyun.admin.mapper.SysRoleMenuMapper;
import com.mikuyun.admin.service.SysRoleMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 角色菜单表 服务实现类
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-05
 */
@Slf4j
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRolePermission(EditRPEvt evt) {
        List<SysRoleMenu> beforeData = this.lambdaQuery().eq(SysRoleMenu::getRoleId, evt.getRoleId()).list();
        // 删除旧权限
        LambdaQueryWrapper<SysRoleMenu> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysRoleMenu::getRoleId, evt.getRoleId());
        this.baseMapper.delete(lambdaQueryWrapper);
        // 新增权限
        List<SysRoleMenu> sysRoleMenuList = evt.getIds().stream().map(item -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(evt.getRoleId());
            sysRoleMenu.setMenuId(item);
            return sysRoleMenu;
        }).toList();
        this.saveBatch(sysRoleMenuList);
        List<Integer> beforeMenuIds = beforeData.stream().map(SysRoleMenu::getMenuId).toList();
        log.info("角色权限更新: roleId:{} \n beforeMenuIds:{} \n afterMenuIds:{}", evt.getRoleId(), beforeMenuIds, evt.getIds());
    }

}
