package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.sysrole.AddRoleToSysUser;
import com.mikuyun.admin.service.SysUserRoleService;
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
 * 用户角色表 前端控制器
 * </p>
 *
 * @author qiseyun
 * @since 2023-05-01
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "管理员角色")
@RequestMapping("/sysUserRole")
public class SysUserRoleController {

    private final SysUserRoleService sysUserRoleService;

    /**
     * 给管理员新增角色
     */
    @SaCheckRole(value = {"admin", "super_admin"})
    @PostMapping(value = "/addRoleToSysUser")
    @Operation(summary = "给管理员新增角色")
    public R<Void> addRoleToSysUser(@RequestBody @Valid AddRoleToSysUser evt) {
        sysUserRoleService.addRoleToSysUser(evt);
        return R.ok();
    }

}
