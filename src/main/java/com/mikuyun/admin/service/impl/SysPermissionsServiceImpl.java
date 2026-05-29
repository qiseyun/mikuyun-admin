package com.mikuyun.admin.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.common.ResultCode;
import com.mikuyun.admin.entity.BaseEntity;
import com.mikuyun.admin.entity.SysPermissions;
import com.mikuyun.admin.entity.SysRole;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.dto.syspermissions.AddOrEditPermissionDto;
import com.mikuyun.admin.exception.ServiceException;
import com.mikuyun.admin.mapper.SysPermissionsMapper;
import com.mikuyun.admin.service.SysPermissionsService;
import com.mikuyun.admin.service.SysRoleService;
import com.mikuyun.admin.util.TreeUtils;
import com.mikuyun.admin.vo.syspermissions.SysPermissionListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author mikuyun
 * @since 2022-11-05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysPermissionsServiceImpl extends ServiceImpl<SysPermissionsMapper, SysPermissions> implements SysPermissionsService {

    private final SysRoleService sysRoleService;

    @Override
    public List<String> sysRolePermissions(Integer sysUserId) {
        // 查询登录用户的角色列表
        List<Integer> collect = sysRoleService.querySysRoleInfo(sysUserId)
                .stream()
                .map(SysRole::getId)
                .collect(Collectors.toList());
        if (CollectionUtil.isEmpty(collect)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(baseMapper.sysRolePermissions(collect));
    }

    @Override
    public List<SysPermissionListVo> queryPermissionList(IdDto dto) {
        // 页面组件列表
        List<SysPermissionListVo> sysPermissionList = baseMapper.queryPermissionList(dto.getId());
        // 构建树结构
        SysPermissionListVo root = TreeUtils.getRoot(SysPermissionListVo.class);
        sysPermissionList = TreeUtils.buildTree(sysPermissionList, root);
        return sysPermissionList.get(0).getChildren();
    }

    @Override
    public void addPermission(AddOrEditPermissionDto dto) {
        SysPermissions sysPermissions = new SysPermissions();
        BeanUtil.copyProperties(dto, sysPermissions, "id");
        sysPermissions.setCreateBy(Integer.valueOf(StpUtil.getLoginId().toString()));
        this.save(sysPermissions);
    }

    @Override
    public void updatePermission(AddOrEditPermissionDto dto) {
        if (ObjectUtil.isEmpty(dto.getId())) {
            throw new ServiceException(ResultCode.PARAM_ERROR);
        }
        SysPermissions permission = this.getById(dto.getId());
        String beforeData = JSON.toJSONString(permission);
        permission.setName(dto.getName());
        permission.setPermission(dto.getPermission());
        permission.setKeepAlive(dto.getKeepAlive());
        permission.setDescribe(dto.getDescribe());
        permission.setUpdateBy(Integer.valueOf(StpUtil.getLoginId().toString()));
        permission.setGmtModified(LocalDateTime.now());
        this.updateById(permission);
        String afterData = JSON.toJSONString(permission);
        log.info("权限编辑: id={} \n beforeData={} \n afterData={}", dto.getId(), beforeData, afterData);
    }

    @Override
    public void delete(IdDto dto) {
        this.lambdaUpdate()
                .set(BaseEntity::getIsDelete, 1)
                .set(BaseEntity::getUpdateBy, Integer.valueOf(StpUtil.getLoginId().toString()))
                .set(BaseEntity::getGmtModified, LocalDateTime.now())
                .eq(SysPermissions::getId, dto.getId())
                .update();
    }

    @Override
    public List<Integer> getRolePermissions(Integer roleId) {
        // 查询权限列表
        return this.baseMapper.getRolePermissionIds(roleId);
    }

}
