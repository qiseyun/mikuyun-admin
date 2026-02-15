package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.evt.sysmenu.AddOrEditMenuOrButtonEvt;
import com.mikuyun.admin.service.SysMenuService;
import com.mikuyun.admin.vo.sysmenu.SysMenuListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-05
 */
@Tag(name = "系统菜单管理")
@RestController
@RequestMapping("/sysMenu")
@RequiredArgsConstructor
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @SaCheckRole(value = {"super_admin"}, mode = SaMode.OR)
    @GetMapping(value = "/tree")
    @Operation(summary = "获取系统权限树", description = "-1,除接口外的所有权限; 获取下级就传id,0就是根节点")
    public R<List<SysMenuListVo>> getSysMenuTree(IdEvt evt) {
        return R.ok(sysMenuService.queryMenuList(evt));
    }

    @SaCheckRole(value = {"super_admin"}, mode = SaMode.OR)
    @PostMapping(value = "/add")
    @Operation(summary = "新增权限")
    public R<Void> add(@RequestBody @Valid AddOrEditMenuOrButtonEvt evt) {
        sysMenuService.addMenu(evt);
        return R.ok();
    }

    @SaCheckRole(value = {"super_admin"}, mode = SaMode.OR)
    @PostMapping(value = "/update")
    @Operation(summary = "编辑权限")
    public R<Void> update(@RequestBody @Valid AddOrEditMenuOrButtonEvt evt) {
        sysMenuService.updateMenu(evt);
        return R.ok();
    }

    @SaCheckRole(value = {"super_admin"}, mode = SaMode.OR)
    @PostMapping(value = "/del")
    @Operation(summary = "删除权限")
    public R<Void> delete(@RequestBody @Valid IdEvt evt) {
        sysMenuService.delete(evt);
        return R.ok();
    }

    @GetMapping(value = "/rolePermissions/{roleId}")
    @Operation(summary = "获取角色权限id列表")
    public R<List<Integer>> getRolePermissions(@PathVariable Integer roleId) {
        return R.ok(sysMenuService.getRolePermissions(roleId));
    }

}
