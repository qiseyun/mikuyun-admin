package com.mikuyun.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.SysUser;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.dto.LoginDto;
import com.mikuyun.admin.dto.sysuser.AddSysUserDto;
import com.mikuyun.admin.dto.sysuser.SysUserListDto;
import com.mikuyun.admin.dto.sysuser.UpdateMyInfoDto;
import com.mikuyun.admin.dto.sysuser.UpdateSysUserDto;
import com.mikuyun.admin.vo.SysUserInfo;
import com.mikuyun.admin.vo.UserTokenVo;
import com.mikuyun.admin.vo.sysuser.SysUserListVo;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author mikuyun
 * @since 2022-11-07
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 管理员登录逻辑
     *
     * @param dto 登陆参数
     * @return 管理员信息
     */
    UserTokenVo sysAdminLogin(LoginDto dto);

    /**
     * 管理员信息
     *
     * @param sysUserId 用户id
     * @return 管理员信息
     */
    SysUserInfo getSysUserInfo(Integer sysUserId);

    /**
     * 管理员列表
     *
     * @param dto 查询参数
     * @return List<SysUserListVo>
     */
    List<SysUserListVo> getSysUserList(SysUserListDto dto);

    /**
     * 新增管理员
     *
     * @param dto 新增参数
     */
    void addSysUser(AddSysUserDto dto);

    /**
     * 新增管理员
     *
     * @param dto 新增参数
     */
    void updateSysUser(UpdateSysUserDto dto);

    /**
     * 新增管理员
     *
     * @param dto 新增参数
     */
    void delSysUser(IdDto dto);

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

    /**
     * 修改我的信息
     *
     * @param dto 我的信息
     */
    void updateMyInfo(UpdateMyInfoDto dto);

}
