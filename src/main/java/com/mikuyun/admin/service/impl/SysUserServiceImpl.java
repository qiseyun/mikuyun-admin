package com.mikuyun.admin.service.impl;


import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.common.CacheConstants;
import com.mikuyun.admin.common.CommonConstants;
import com.mikuyun.admin.common.ResultCode;
import com.mikuyun.admin.config.WebConfigProperties;
import com.mikuyun.admin.entity.SysUser;
import com.mikuyun.admin.enums.UserTypeEnum;
import com.mikuyun.admin.evt.LoginEvt;
import com.mikuyun.admin.evt.sysuser.AddSysUserEvt;
import com.mikuyun.admin.exception.ServiceException;
import com.mikuyun.admin.mapper.SysUserMapper;
import com.mikuyun.admin.service.AsyncService;
import com.mikuyun.admin.service.SysUserService;
import com.mikuyun.admin.util.SatokenUserUtils;
import com.mikuyun.admin.vo.UserInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-07
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final WebConfigProperties webConfigProperties;

    private final AsyncService asyncService;

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public UserInfo sysAdminLogin(LoginEvt evt) {
        SysUser sysUser = this.lambdaQuery()
                .eq(SysUser::getUsername, evt.getUsername())
                .eq(SysUser::getIsDelete, CommonConstants.STATUS_NORMAL_INT)
                .one();
        if (ObjectUtil.isEmpty(sysUser)) {
            throw new ServiceException(ResultCode.LOGIN_ERROR);
        }
        if (sysUser.getIsDelete().equals(CommonConstants.STATUS_DEL_INT)) {
            throw new ServiceException(ResultCode.USER_WRITE_OFF);
        }
        // 解密后对比
        String pwd = SaSecureUtil.aesDecrypt(sysUser.getSalt(), sysUser.getPassword());
        if (ObjectUtil.notEqual(evt.getPassword(), pwd)) {
            throw new ServiceException(ResultCode.LOGIN_ERROR);
        }
        // 获取当前的角色列表和角色的权限列表
        List<String> roleList = StpUtil.getRoleList(sysUser.getId());
        List<String> permissionList = StpUtil.getPermissionList(sysUser.getId());
        // 构建用户信息
        UserInfo adminUserInfo = getAdminUserInfo(sysUser, roleList, permissionList);
        // 构建用户session
        SatokenUserUtils.userSessionBuild(adminUserInfo);
        // 发送邮件
//        asyncService.loginMail(
//                StpUtil.getLoginDeviceType(),
//                LocalDateTime.now(),
//                adminUserInfo.getEmail(),
//                sysUser.getUsername()
//        );
        return adminUserInfo;
    }

    private UserInfo getAdminUserInfo(SysUser sysUser, List<String> roleList, List<String> permissionList) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(sysUser.getId());
        userInfo.setTelephone(sysUser.getPhone());
        userInfo.setRealName(sysUser.getRealName());
        userInfo.setHeadPortrait(sysUser.getAvatar());
        userInfo.setEmail(sysUser.getEmail());
        userInfo.setRoleList(roleList);
        userInfo.setPermissionList(permissionList);
        userInfo.setUserType(sysUser.getUserType());
        return userInfo;
    }

    @Override
    public void addSysUser(AddSysUserEvt evt) {
        // 对称加密
        String pwd = SaSecureUtil.aesEncrypt(webConfigProperties.getSalt(), evt.getPassword());
        SysUser sysUser = new SysUser();
        sysUser.setUsername(evt.getUsername());
        sysUser.setPassword(pwd);
        sysUser.setRealName(evt.getRealName());
        sysUser.setSalt(webConfigProperties.getSalt());
        sysUser.setPhone(evt.getTelephone());
        sysUser.setCreateBy(SatokenUserUtils.getUserInfo().getId());
        sysUser.setEmail(evt.getEmail());
        sysUser.setUserType(UserTypeEnum.REGULAR_USERS.getType());
        this.save(sysUser);
        log.info("新增管理员; id: {}", sysUser.getId());
    }

    @Override
    public void logOutBusiness() {
        clearCache();
        // 登出
        StpUtil.logout();
    }

    @Override
    public void logOutBusiness(Integer adminId) {
        clearCache();
        // 登出
        StpUtil.logout(adminId);
    }

    private void clearCache() {
        Integer sysUserId = SatokenUserUtils.getUserInfo().getId();
        // 删除相关缓存 ->
        // 菜单缓存
        String menuKey = CacheConstants.MENU_TREE + sysUserId;
        stringRedisTemplate.delete(menuKey);
    }

}
