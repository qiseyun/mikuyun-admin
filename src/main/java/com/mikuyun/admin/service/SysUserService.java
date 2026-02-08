package com.mikuyun.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.SysUser;
import com.mikuyun.admin.evt.LoginEvt;
import com.mikuyun.admin.evt.sysuser.AddSysUserEvt;
import com.mikuyun.admin.evt.sysuser.SysUserListEvt;
import com.mikuyun.admin.vo.SysUserInfo;
import com.mikuyun.admin.vo.UserTokenVo;
import com.mikuyun.admin.vo.sys_user.SysUserListVo;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-07
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 管理员登录逻辑
     *
     * @param evt 登陆参数
     * @return 管理员信息
     */
    UserTokenVo sysAdminLogin(LoginEvt evt);

    /**
     * 管理员信息
     *
     * @param sysUserId 用户id
     * @return 管理员信息
     */
    SysUserInfo getSysUserInfo(Object sysUserId);

    /**
     * 管理员列表
     *
     * @param evt 查询参数
     * @return List<SysUserListVo>
     */
    List<SysUserListVo> getSysUserList(SysUserListEvt evt);

    /**
     * 新增管理员
     *
     * @param evt 新增参数
     */
    void addSysUser(AddSysUserEvt evt);

    /**
     * 登出业务相关
     */
    void logOutBusiness();

    /**
     * 登出业务相关
     *
     * @param adminId 用户id
     */
    void logOutBusiness(Integer adminId);

}
