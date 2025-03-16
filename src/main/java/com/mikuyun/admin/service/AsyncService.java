package com.mikuyun.admin.service;


import java.time.LocalDateTime;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年3月27日/0025 0点17分
 */
public interface AsyncService {

    /**
     * @param facility    登录设备
     * @param landingTime 登陆时间
     * @param to          收件人
     * @param username    用户名
     */
    void loginMail(String facility, LocalDateTime landingTime, String to, String username);

}
