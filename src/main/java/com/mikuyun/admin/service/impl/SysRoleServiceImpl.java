package com.mikuyun.admin.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.BaseEntity;
import com.mikuyun.admin.entity.SysRole;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.evt.sysrole.AddSysRoleListEvt;
import com.mikuyun.admin.evt.sysrole.SysRoleEvt;
import com.mikuyun.admin.evt.sysrole.UpdateSysRoleEvt;
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
 * @author qiseyun
 * @since 2022-11-05
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> querySysRoleInfo(Object sysUserId) {
        return baseMapper.querySysRoleInfo(sysUserId);
    }

    @Override
    public List<String> querySysRole(Object sysUserId) {
        return baseMapper.querySysRole(sysUserId);
    }

    @Override
    public List<QuerySysRoleListVo> queryRoleList(SysRoleEvt evt) {
        evt.initPageParamsNoRestrictions();
        return baseMapper.queryRoleList(evt);
    }

    @Override
    public void addSysRole(AddSysRoleListEvt evt) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(evt, sysRole);
        sysRole.setCreateBy(Integer.parseInt(StpUtil.getLoginId().toString()));
        this.save(sysRole);
    }

    @Override
    public void updateSysRole(UpdateSysRoleEvt evt) {
        this.getById(evt.getId());
        SysRole sysRole = this.getById(evt.getId());
        String beforeData = JSON.toJSONString(sysRole);
        BeanUtil.copyProperties(evt, sysRole);
        sysRole.setUpdateBy(Integer.parseInt(StpUtil.getLoginId().toString()));
        sysRole.setGmtModified(LocalDateTime.now());
        this.updateById(sysRole);
        String afterData = JSON.toJSONString(sysRole);
        log.info("\n系统角色更新: \n beforeData: {} \n afterData:{}", beforeData, afterData);
    }

    @Override
    public void delSysRole(IdEvt evt) {
        this.lambdaUpdate()
                .set(SysRole::getIsDelete, 1)
                .set(BaseEntity::getUpdateBy, StpUtil.getLoginId())
                .set(BaseEntity::getGmtModified, LocalDateTime.now())
                .eq(SysRole::getId, evt.getId())
                .update();
    }

}
