package com.mikuyun.admin.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.entity.SysUserRole;
import com.mikuyun.admin.evt.sysrole.AddRoleToSysUser;
import com.mikuyun.admin.mapper.SysUserRoleMapper;
import com.mikuyun.admin.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author qiseyun
 * @since 2023-05-01
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public void addRoleToSysUser(AddRoleToSysUser evt) {
        SysUserRole sysUserRole = new SysUserRole();
        BeanUtil.copyProperties(evt, sysUserRole);
        this.save(sysUserRole);
    }

}
