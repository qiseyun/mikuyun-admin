package com.mikuyun.admin.util;


import cn.dev33.satoken.stp.StpUtil;
import com.mikuyun.admin.common.Constant;
import com.mikuyun.admin.vo.UserInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2022/11/3 15:47
 */
@Slf4j
public class SatokenUserUtils {

    public static void userSessionBuild(UserInfo userInfo) {
        StpUtil.login(userInfo.getId());
        String tokenValue = StpUtil.getTokenValue();
        userInfo.setAuthorization(tokenValue);
        // Account-Session
        StpUtil.getSession().set(Constant.Satoken.ADMIN_SESSION_KEY, userInfo);
        // Token-Session
        StpUtil.getTokenSessionByToken(tokenValue).set(Constant.Satoken.ADMIN_SESSION_KEY, userInfo);
    }

    public static UserInfo getUserInfo() {
        return (UserInfo) StpUtil.getSession().get(Constant.Satoken.ADMIN_SESSION_KEY);
    }

    public static UserInfo getUserInfoByToken(String token) {
        return (UserInfo) StpUtil.getTokenSessionByToken(token).get(Constant.Satoken.ADMIN_SESSION_KEY);
    }

}
