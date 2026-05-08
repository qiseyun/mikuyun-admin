package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.dto.sysrole.AddSysRoleListDto;
import com.mikuyun.admin.dto.sysrole.SysRoleDto;
import com.mikuyun.admin.dto.sysrole.UpdateSysRoleDto;
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
 * @author mikuyun
 * @since 2022-11-05
 */
@Tag(name = "系统角色管理")
@RestController
@RequestMapping("/sysRole")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @SaCheckPermission(value = "system:role:page_view")
    @GetMapping(value = "/getRoleList")
    @Operation(summary = "获取系统角色列表")
    public R<List<QuerySysRoleListVo>> getRoleList(SysRoleDto dto) {
        return R.ok(sysRoleService.queryRoleList(dto));
    }

    @SaCheckPermission(value = "system:role:add")
    @PostMapping(value = "/add")
    @Operation(summary = "新增角色")
    public R<Void> addSysRole(@RequestBody @Valid AddSysRoleListDto dto) {
        sysRoleService.addSysRole(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:role:edit")
    @PostMapping(value = "/update")
    @Operation(summary = "修改角色")
    public R<Void> updateSysRole(@RequestBody @Valid UpdateSysRoleDto dto) {
        sysRoleService.updateSysRole(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:role:delete")
    @PostMapping(value = "/del")
    @Operation(summary = "删除角色")
    public R<Void> delSysRole(@RequestBody @Valid IdDto dto) {
        sysRoleService.delSysRole(dto);
        return R.ok();
    }

}
