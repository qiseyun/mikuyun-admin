package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.dto.syspermissions.AddOrEditPermissionDto;
import com.mikuyun.admin.service.SysPermissionsService;
import com.mikuyun.admin.vo.syspermissions.SysPermissionListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author mikuyun
 * @since 2022-11-05
 */
@Tag(name = "系统菜单管理")
@RestController
@RequestMapping("/sysPermissions")
@RequiredArgsConstructor
public class SysPermissionsController {

    private final SysPermissionsService sysPermissionsService;

    @SaCheckPermission(value = "system:permission:page_view")
    @GetMapping(value = "/tree")
    @Operation(summary = "获取系统权限树", description = "-1,除接口外的所有权限; 获取下级就传id,0就是根节点")
    public R<List<SysPermissionListVo>> getSysPermissionTree(IdDto dto) {
        return R.ok(sysPermissionsService.queryPermissionList(dto));
    }

    @SaCheckPermission(value = "system:permission:add")
    @PostMapping(value = "/add")
    @Operation(summary = "新增权限")
    public R<Void> add(@RequestBody @Valid AddOrEditPermissionDto dto) {
        sysPermissionsService.addPermission(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:permission:edit")
    @PostMapping(value = "/update")
    @Operation(summary = "编辑权限")
    public R<Void> update(@RequestBody @Valid AddOrEditPermissionDto dto) {
        sysPermissionsService.updatePermission(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:permission:delete")
    @PostMapping(value = "/del")
    @Operation(summary = "删除权限")
    public R<Void> delete(@RequestBody @Valid IdDto dto) {
        sysPermissionsService.delete(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:role:page_view")
    @GetMapping(value = "/rolePermissions/{roleId}")
    @Operation(summary = "获取角色权限id列表")
    public R<List<Integer>> getRolePermissions(@PathVariable Integer roleId) {
        return R.ok(sysPermissionsService.getRolePermissions(roleId));
    }

}
