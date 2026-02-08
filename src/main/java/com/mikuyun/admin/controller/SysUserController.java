package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.evt.sysuser.AddSysUserEvt;
import com.mikuyun.admin.evt.sysuser.SysUserListEvt;
import com.mikuyun.admin.evt.sysuser.UpdateSysUserEvt;
import com.mikuyun.admin.evt.user.AddUserEvt;
import com.mikuyun.admin.service.SysUserService;
import com.mikuyun.admin.service.UserService;
import com.mikuyun.admin.vo.sysuser.SysUserListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-07
 */
@Tag(name = "系统用户管理")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/sysUser")
public class SysUserController {

    private final SysUserService sysUserService;

    private final UserService userService;

    @GetMapping(value = "/list")
    @Operation(summary = "后台用户列表")
    public R<List<SysUserListVo>> list(SysUserListEvt evt) {
        return R.ok(sysUserService.getSysUserList(evt));
    }

    @SaCheckRole("super_admin")
    @PostMapping(value = "/add")
    @Operation(summary = "新增后台用户")
    public R<Void> addUser(@RequestBody @Valid AddSysUserEvt evt) {
        sysUserService.addSysUser(evt);
        return R.ok();
    }

    @SaCheckRole("super_admin")
    @PostMapping(value = "/update")
    @Operation(summary = "编辑后台用户")
    public R<Void> updateSysUser(@RequestBody @Valid UpdateSysUserEvt evt) {
        sysUserService.updateSysUser(evt);
        return R.ok();
    }

    @SaCheckRole("super_admin")
    @PostMapping(value = "/del")
    @Operation(summary = "删除后台用户")
    public R<Void> delSysUser(@RequestBody @Valid IdEvt evt) {
        sysUserService.delSysUser(evt);
        return R.ok();
    }

    @SaCheckPermission("add_user")
    @PostMapping(value = "/addClientUser")
    @Operation(summary = "新增用户")
    public R<Void> addUser(@RequestBody @Valid AddUserEvt evt) {
        userService.add(evt);
        return R.ok();
    }

}
