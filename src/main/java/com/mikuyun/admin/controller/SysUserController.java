package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.dto.sysuser.AddSysUserDto;
import com.mikuyun.admin.dto.sysuser.SysUserListDto;
import com.mikuyun.admin.dto.sysuser.UpdateMyInfoDto;
import com.mikuyun.admin.dto.sysuser.UpdateSysUserDto;
import com.mikuyun.admin.service.SysUserService;
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
 * @author mikuyun
 * @since 2022-11-07
 */
@Tag(name = "系统用户管理")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/sysUser")
public class SysUserController {

    private final SysUserService sysUserService;

    @SaCheckPermission(value = "system:user:page_view")
    @GetMapping(value = "/list")
    @Operation(summary = "后台用户列表")
    public R<List<SysUserListVo>> list(SysUserListDto dto) {
        return R.ok(sysUserService.getSysUserList(dto));
    }

    @SaCheckPermission(value = "system:user:add")
    @PostMapping(value = "/add")
    @Operation(summary = "新增后台用户")
    public R<Void> addUser(@RequestBody @Valid AddSysUserDto dto) {
        sysUserService.addSysUser(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:user:edit")
    @PostMapping(value = "/update")
    @Operation(summary = "编辑后台用户")
    public R<Void> updateSysUser(@RequestBody @Valid UpdateSysUserDto dto) {
        sysUserService.updateSysUser(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:user:delete", mode = SaMode.OR)
    @PostMapping(value = "/del")
    @Operation(summary = "删除后台用户")
    public R<Void> delSysUser(@RequestBody @Valid IdDto dto) {
        sysUserService.delSysUser(dto);
        return R.ok();
    }

    @PostMapping(value = "/updateMy")
    @Operation(summary = "编辑个人信息")
    public R<Void> updateSysUser(@RequestBody @Valid UpdateMyInfoDto dto) {
        sysUserService.updateMyInfo(dto);
        return R.ok();
    }

}
