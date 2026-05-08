package com.mikuyun.admin.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.BaseEntity;
import com.mikuyun.admin.entity.SysRole;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.dto.sysrole.AddSysRoleListDto;
import com.mikuyun.admin.dto.sysrole.SysRoleDto;
import com.mikuyun.admin.dto.sysrole.UpdateSysRoleDto;
import com.mikuyun.admin.mapper.SysRoleMapper;
import com.mikuyun.admin.service.SysRoleService;
import com.mikuyun.admin.vo.sysrole.QuerySysRoleListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author mikuyun
 * @since 2022-11-05
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> querySysRoleInfo(Integer sysUserId) {
        return baseMapper.querySysRoleInfo(sysUserId);
    }

    @Override
    public List<String> querySysRole(Integer sysUserId) {
        return baseMapper.querySysRole(sysUserId);
    }

    @Override
    public List<QuerySysRoleListVo> queryRoleList(SysRoleDto dto) {
        dto.initPageParamsNoRestrictions();
        return baseMapper.queryRoleList(dto);
    }

    @Override
    public void addSysRole(AddSysRoleListDto dto) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(dto, sysRole);
        sysRole.setCreateBy(Integer.parseInt(StpUtil.getLoginId().toString()));
        this.save(sysRole);
    }

    @Override
    public void updateSysRole(UpdateSysRoleDto dto) {
        this.getById(dto.getId());
        SysRole sysRole = this.getById(dto.getId());
        String beforeData = JSON.toJSONString(sysRole);
        BeanUtil.copyProperties(dto, sysRole);
        sysRole.setUpdateBy(Integer.parseInt(StpUtil.getLoginId().toString()));
        sysRole.setGmtModified(LocalDateTime.now());
        this.updateById(sysRole);
        String afterData = JSON.toJSONString(sysRole);
        log.info("\n系统角色更新: \n beforeData: {} \n afterData:{}", beforeData, afterData);
    }

    @Override
    public void delSysRole(IdDto dto) {
        this.lambdaUpdate()
                .set(SysRole::getIsDelete, 1)
                .set(BaseEntity::getUpdateBy, StpUtil.getLoginId())
                .set(BaseEntity::getGmtModified, LocalDateTime.now())
                .eq(SysRole::getId, dto.getId())
                .update();
    }

}
