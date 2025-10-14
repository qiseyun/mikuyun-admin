package com.mikuyun.admin.service.impl;


import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.common.Constant;
import com.mikuyun.admin.common.ResultCode;
import com.mikuyun.admin.entity.SysUser;
import com.mikuyun.admin.enums.UserTypeEnum;
import com.mikuyun.admin.evt.LoginEvt;
import com.mikuyun.admin.evt.sysuser.AddSysUserEvt;
import com.mikuyun.admin.exception.ServiceException;
import com.mikuyun.admin.mapper.SysUserMapper;
import com.mikuyun.admin.properties.WebConfigProperties;
import com.mikuyun.admin.service.AsyncService;
import com.mikuyun.admin.service.SysUserService;
import com.mikuyun.admin.vo.UserInfo;
import com.mikuyun.admin.vo.UserTokenVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

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
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final WebConfigProperties webConfigProperties;

    private final AsyncService asyncService;

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public UserTokenVo sysAdminLogin(LoginEvt evt) {
        SysUser sysUser = this.lambdaQuery()
                .eq(SysUser::getUsername, evt.getUsername())
                .eq(SysUser::getIsDelete, Constant.STATUS_NORMAL_INT)
                .one();
        if (ObjectUtil.isEmpty(sysUser)) {
            throw new ServiceException(ResultCode.LOGIN_ERROR);
        }
        if (sysUser.getIsDelete().equals(Constant.STATUS_DEL_INT)) {
            throw new ServiceException(ResultCode.USER_WRITE_OFF);
        }
        // 解密后对比
        String pwd = SaSecureUtil.aesDecrypt(sysUser.getSalt(), sysUser.getPassword());
        if (ObjectUtil.notEqual(evt.getPassword(), pwd)) {
            throw new ServiceException(ResultCode.LOGIN_ERROR);
        }
        // 登陆返回token信息
        return login(sysUser.getId());
    }

    @Override
    public UserInfo getSysUserInfo(Object sysUserId) {
        SysUser sysUser = this.getById((Serializable) sysUserId);
        if (ObjectUtil.isEmpty(sysUser)) {
            throw new ServiceException(ResultCode.DATA_NOT_EXIST);
        }
        // 获取当前的角色列表和角色的权限列表
//        List<String> roleList = StpUtil.getRoleList(sysUser.getId());
//        List<String> permissionList = StpUtil.getPermissionList(sysUser.getId());
        return getAdminUserInfo(sysUser);
    }

    /**
     * 登陆
     *
     * @param sysUserId 用户id
     * @return UserTokenVo
     */
    private UserTokenVo login(Integer sysUserId) {
        UserTokenVo userToken = new UserTokenVo();
        StpUtil.login(sysUserId);
        String tokenValue = StpUtil.getTokenValue();
        long timestamp = System.currentTimeMillis() / 1000;
        userToken.setAccessToken(tokenValue);
        userToken.setExpiresTime(timestamp + StpUtil.getTokenTimeout());
        return userToken;
    }

    /**
     * set用户信息
     *
     * @param sysUser sysUser
     * @return UserInfo
     */
    private UserInfo getAdminUserInfo(SysUser sysUser) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(sysUser.getId());
        userInfo.setTelephone(sysUser.getPhone());
        userInfo.setUsername(sysUser.getUsername());
        userInfo.setRealName(sysUser.getRealName());
        userInfo.setHeadPortrait(sysUser.getAvatar());
        userInfo.setEmail(sysUser.getEmail());
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
        sysUser.setCreateBy(Integer.parseInt(StpUtil.getLoginId().toString()));
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

    /**
     * 删除缓存
     */
    private void clearCache() {
        // 删除菜单缓存
        String menuKey = Constant.CacheConstants.MENU_TREE + StpUtil.getLoginId();
        stringRedisTemplate.delete(menuKey);
    }

}
