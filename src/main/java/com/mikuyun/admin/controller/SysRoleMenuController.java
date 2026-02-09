package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.sysmenu.EditRPEvt;
import com.mikuyun.admin.service.SysRoleMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 角色菜单表 前端控制器
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-05
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "角色权限关联")
@RequestMapping("/sysRP")
public class SysRoleMenuController {

    private final SysRoleMenuService sysRoleMenuService;

    @SaCheckRole(value = {"super_admin"})
    @PostMapping(value = "/edit")
    @Operation(summary = "角色权限编辑")
    public R<Void> addMenuToRole(@RequestBody @Valid EditRPEvt evt) {
        sysRoleMenuService.editRolePermission(evt);
        return R.ok();
    }

}
