package com.mikuyun.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.SysRolePermissions;
import com.mikuyun.admin.evt.syspermissions.EditRPEvt;
import com.mikuyun.admin.mapper.SysRolePermissionsMapper;
import com.mikuyun.admin.service.SysRolePermissionsService;
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
public class SysRolePermissionsServiceImpl extends ServiceImpl<SysRolePermissionsMapper, SysRolePermissions> implements SysRolePermissionsService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRolePermission(EditRPEvt evt) {
        List<SysRolePermissions> beforeData = this.lambdaQuery().eq(SysRolePermissions::getRoleId, evt.getRoleId()).list();
        // 删除旧权限
        LambdaQueryWrapper<SysRolePermissions> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysRolePermissions::getRoleId, evt.getRoleId());
        this.baseMapper.delete(lambdaQueryWrapper);
        // 新增权限
        List<SysRolePermissions> sysRolePermissionsList = evt.getIds().stream().map(item -> {
            SysRolePermissions sysRolePermissions = new SysRolePermissions();
            sysRolePermissions.setRoleId(evt.getRoleId());
            sysRolePermissions.setPermissionId(item);
            return sysRolePermissions;
        }).toList();
        this.saveBatch(sysRolePermissionsList);
        List<Integer> beforePermissionIds = beforeData.stream().map(SysRolePermissions::getPermissionId).toList();
        log.info("角色权限更新: roleId:{} \n beforePermissionIds:{} \n afterPermissionIds:{}", evt.getRoleId(), beforePermissionIds, evt.getIds());
    }

}
