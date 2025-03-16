package com.mikuyun.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mikuyun.admin.entity.User;
import com.mikuyun.admin.evt.user.AddUserEvt;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-01
 */
public interface UserService extends IService<User> {

    /**
     * 新增用户
     *
     * @param user 用户参数
     */
    void add(AddUserEvt user);

}
