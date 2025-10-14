package com.mikuyun.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.LoginEvt;
import com.mikuyun.admin.service.SysMenuService;
import com.mikuyun.admin.service.SysUserService;
import com.mikuyun.admin.vo.UserInfo;
import com.mikuyun.admin.vo.UserTokenVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author qiseyun
 * @version 1.0
 * @date 2022/11/1 13:33
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "系统用户login")
@RequestMapping(value = "/auth")
public class SysUserLoginController {

    private final SysUserService sysUserService;

    private final SysMenuService sysMenuService;

    @PostMapping(value = "/login")
    @Operation(summary = "管理员登录")
    public R<UserTokenVo> sysLogin(@RequestBody LoginEvt evt) {
        return R.ok(sysUserService.sysAdminLogin(evt));
    }

    @GetMapping(value = "/permissions")
    @Operation(summary = "查询权限列表")
    public R<List<String>> sysLogin() {
        return R.ok(sysMenuService.sysRoleMenuPermissions(StpUtil.getLoginId()));
    }

    /**
     * 查询登录用户信息
     */
    @GetMapping(value = "/getInfo")
    @Operation(summary = "查询登录用户信息")
    public R<UserInfo> getInfo() {
        return R.ok(sysUserService.getSysUserInfo(StpUtil.getLoginId()));
    }

    /**
     * 退出登录
     */
    @PostMapping(value = "/logout")
    @Operation(summary = "退出登录")
    public R<Void> logout() {
        sysUserService.logOutBusiness();
        return R.ok();
    }

    /**
     * 指定用户退出登录
     *
     * @param adminId 用户id
     */
    @SaCheckRole(value = "super_admin")
    @PostMapping(value = "/logout/{adminId}")
    @Operation(summary = "指定用户退出登录")
    public R<Void> logoutById(@PathVariable Integer adminId) {
        sysUserService.logOutBusiness(adminId);
        return R.ok();
    }

}
