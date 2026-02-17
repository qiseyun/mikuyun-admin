package com.mikuyun.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.SysUserRole;
import com.mikuyun.admin.evt.sysuser.EditUserRoleEvt;
import com.mikuyun.admin.mapper.SysUserRoleMapper;
import com.mikuyun.admin.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author qiseyun
 * @since 2023-05-01
 */
@Slf4j
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public List<Integer> getRoles(Integer sysUserId) {
        return this.lambdaQuery()
                .eq(SysUserRole::getUserId, sysUserId)
                .list()
                .stream()
                .map(SysUserRole::getRoleId)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRoles(EditUserRoleEvt evt) {
        List<Integer> beforeRoleIds = this.lambdaQuery()
                .eq(SysUserRole::getUserId, evt.getSysUserId())
                .list()
                .stream()
                .map(SysUserRole::getRoleId)
                .toList();
        // 删除旧角色
        LambdaQueryWrapper<SysUserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUserRole::getUserId, evt.getSysUserId());
        this.getBaseMapper().delete(lambdaQueryWrapper);
        // 更新角色
        List<SysUserRole> afterData = evt.getRoleIds()
                .stream()
                .map(item -> new SysUserRole(evt.getSysUserId(), item))
                .toList();
        this.saveBatch(afterData);
        log.info("用户角色更新: sysUserId:{} \n beforeRoleIds:{} \n afterPermissionIds:{}", evt.getSysUserId(), beforeRoleIds, evt.getRoleIds());
    }

}
