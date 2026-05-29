package com.mikuyun.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.SysUserRole;
import com.mikuyun.admin.dto.sysuser.EditUserRoleDto;
import com.mikuyun.admin.mapper.SysUserRoleMapper;
import com.mikuyun.admin.service.SysUserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author mikuyun
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
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRoles(EditUserRoleDto dto) {
        List<Integer> beforeRoleIds = this.lambdaQuery()
                .eq(SysUserRole::getUserId, dto.getSysUserId())
                .list()
                .stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
        // 删除旧角色
        LambdaQueryWrapper<SysUserRole> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SysUserRole::getUserId, dto.getSysUserId());
        this.getBaseMapper().delete(lambdaQueryWrapper);
        // 更新角色
        List<SysUserRole> afterData = dto.getRoleIds()
                .stream()
                .map(item -> new SysUserRole(dto.getSysUserId(), item))
                .collect(Collectors.toList());
        this.saveBatch(afterData);
        log.info("用户角色更新: sysUserId:{} \n beforeRoleIds:{} \n afterPermissionIds:{}", dto.getSysUserId(), beforeRoleIds, dto.getRoleIds());
    }

}
