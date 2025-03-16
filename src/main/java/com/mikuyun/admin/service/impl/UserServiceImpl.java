package com.mikuyun.admin.service.impl;


import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mikuyun.admin.common.ResultCode;
import com.mikuyun.admin.config.WebConfigProperties;
import com.mikuyun.admin.entity.User;
import com.mikuyun.admin.evt.user.AddUserEvt;
import com.mikuyun.admin.exception.ServiceException;
import com.mikuyun.admin.mapper.UserMapper;
import com.mikuyun.admin.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-01
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final WebConfigProperties webConfigProperties;

    @Override
    public void add(AddUserEvt evt) {
        User user = new User();
        // 对称加密
        String pwd = SaSecureUtil.aesEncrypt(webConfigProperties.getSalt(), evt.getPassword());
        LocalDateTime now = LocalDateTime.now();
        BeanUtil.copyProperties(evt, user);
        user.setPassword(pwd);
        user.setNickname("用户" + now.toInstant(ZoneOffset.of("+8")).toEpochMilli());
        try {
            this.save(user);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.SAVE_FAILED);
        }
    }

}