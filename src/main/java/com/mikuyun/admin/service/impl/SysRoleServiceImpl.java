package com.mikuyun.admin.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.SysRole;
import com.mikuyun.admin.evt.sysrole.AddSysRoleListEvt;
import com.mikuyun.admin.mapper.SysRoleMapper;
import com.mikuyun.admin.service.SysRoleService;
import com.mikuyun.admin.util.SatokenUserUtils;
import com.mikuyun.admin.vo.sysrole.QuerySysRoleListVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-05
 */
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
    public List<QuerySysRoleListVo> queryRoleList() {
        return baseMapper.queryRoleList();
    }

    @Override
    public void addSysRole(AddSysRoleListEvt evt) {
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(evt, sysRole);
        sysRole.setCreateBy(SatokenUserUtils.getUserInfo().getId());
        this.save(sysRole);
    }
}
