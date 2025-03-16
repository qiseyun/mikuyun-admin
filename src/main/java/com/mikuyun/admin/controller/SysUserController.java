package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.sysuser.AddSysUserEvt;
import com.mikuyun.admin.evt.user.AddUserEvt;
import com.mikuyun.admin.service.SysUserService;
import com.mikuyun.admin.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-07
 */
@Tag(name = "系统用户管理")
@AllArgsConstructor
@RestController
@RequestMapping(value = "/sysUser")
public class SysUserController {

    private final SysUserService sysUserService;

    private final UserService userService;

    /**
     * 后台新增用户
     */
    @SaCheckRole("super_admin")
    @PostMapping(value = "/addAdmin")
    @Operation(summary = "新增管理员")
    public R<Void> addUser(@RequestBody AddSysUserEvt evt) {
        sysUserService.addSysUser(evt);
        return R.ok();
    }

    /**
     * 后台新增用户
     */
    @SaCheckPermission("add_user")
    @PostMapping(value = "/addUser")
    @Operation(summary = "新增用户")
    public R<Void> addUser(@RequestBody AddUserEvt evt) {
        userService.add(evt);
        return R.ok();
    }

}
