package com.mikuyun.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.LoginEvt;
import com.mikuyun.admin.service.SysUserService;
import com.mikuyun.admin.util.SatokenUserUtils;
import com.mikuyun.admin.vo.UserInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * @author qiseyun
 * @version 1.0
 * @date 2022/11/1 13:33
 */
@Slf4j
@AllArgsConstructor
@RestController
@Tag(name = "系统用户login")
@RequestMapping(value = "/auth")
public class SysUserLoginController {

    private final SysUserService sysUserService;

    @PostMapping(value = "/login")
    @Operation(summary = "管理员登录")
    public R<UserInfo> sysLogin(@RequestBody LoginEvt evt) {
        return R.ok(sysUserService.sysAdminLogin(evt));
    }

    /**
     * 查询登录信息
     */
    @GetMapping(value = "/getInfo")
    @Operation(summary = "查询登录信息")
    public R<UserInfo> getInfo() {
        return R.ok(SatokenUserUtils.getUserInfo());
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
