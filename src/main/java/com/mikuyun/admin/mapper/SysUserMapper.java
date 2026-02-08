package com.mikuyun.admin.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mikuyun.admin.entity.SysUser;
import com.mikuyun.admin.evt.sysuser.SysUserListEvt;
import com.mikuyun.admin.vo.sysuser.SysUserListVo;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-07
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 用户列表
     *
     * @param evt 查询参数
     * @return List<SysUserListVo>
     */
    List<SysUserListVo> getSysUsers(SysUserListEvt evt);

}
