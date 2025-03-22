package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.evt.sysmenu.AddMenuOrButtonEvt;
import com.mikuyun.admin.service.SysMenuService;
import com.mikuyun.admin.vo.sysmenu.SysMenuListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@AllArgsConstructor
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @SaCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
    @PostMapping(value = "/tree")
    @Operation(summary = "获取系统权限树", description = "0除接口外的所有权限; 获取下级就传id")
    public R<List<SysMenuListVo>> getSysMenuTree(IdEvt evt) {
        return R.ok(sysMenuService.queryMenuList(evt));
    }

    @SaCheckRole(value = {"super_admin", "admin"}, mode = SaMode.OR)
    @PostMapping(value = "/addMenuOrButton")
    @Operation(summary = "新增菜单或按钮")
    public R<Void> addMenuOrButton(@RequestBody @Valid AddMenuOrButtonEvt evt) {
        sysMenuService.addMenuOrButton(evt);
        return R.ok();
    }

    @PostMapping(value = "/myTree")
    @Operation(summary = "登录用户的权限树查询")
    public R<List<SysMenuListVo>> getMenuTree() {
        return R.ok(sysMenuService.getMenuTree());
    }

}
