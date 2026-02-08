package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.evt.sysrole.AddSysRoleListEvt;
import com.mikuyun.admin.evt.sysrole.SysRoleEvt;
import com.mikuyun.admin.evt.sysrole.UpdateSysRoleEvt;
import com.mikuyun.admin.service.SysRoleService;
import com.mikuyun.admin.vo.sysrole.QuerySysRoleListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 系统角色表 前端控制器
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-05
 */
@Tag(name = "系统角色管理")
@RestController
@RequestMapping("/sysRole")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @GetMapping(value = "/getRoleList")
    @Operation(summary = "获取系统角色列表")
    public R<List<QuerySysRoleListVo>> getRoleList(SysRoleEvt evt) {
        return R.ok(sysRoleService.queryRoleList(evt));
    }

    @SaCheckRole(value = "super_admin")
    @PostMapping(value = "/add")
    @Operation(summary = "新增角色")
    public R<Void> addSysRole(@RequestBody @Valid AddSysRoleListEvt evt) {
        sysRoleService.addSysRole(evt);
        return R.ok();
    }

    @SaCheckRole(value = "super_admin")
    @PostMapping(value = "/update")
    @Operation(summary = "修改角色")
    public R<Void> updateSysRole(@RequestBody @Valid UpdateSysRoleEvt evt) {
        sysRoleService.updateSysRole(evt);
        return R.ok();
    }

    @SaCheckRole(value = "super_admin")
    @PostMapping(value = "/del")
    @Operation(summary = "删除角色")
    public R<Void> delSysRole(@RequestBody @Valid IdEvt evt) {
        sysRoleService.delSysRole(evt);
        return R.ok();
    }

}
