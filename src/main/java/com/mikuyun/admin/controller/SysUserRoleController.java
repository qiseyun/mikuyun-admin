package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.dto.sysuser.EditUserRoleDto;
import com.mikuyun.admin.service.SysUserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户角色表 前端控制器
 * </p>
 *
 * @author mikuyun
 * @since 2023-05-01
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "管理员角色")
@RequestMapping("/sysUserRole")
public class SysUserRoleController {

    private final SysUserRoleService sysUserRoleService;

    @SaCheckPermission(value = "system:user:page_view")
    @GetMapping(value = "/getRoles/{sysUserId}")
    @Operation(summary = "获取用户角色")
    public R<List<Integer>> getRoles(@PathVariable Integer sysUserId) {
        return R.ok(sysUserRoleService.getRoles(sysUserId));
    }

    @SaCheckPermission(value = "system:user:edit_role")
    @PostMapping(value = "/editRoles")
    @Operation(summary = "编辑用户角色")
    public R<Void> editRoles(@RequestBody @Valid EditUserRoleDto dto) {
        sysUserRoleService.editRoles(dto);
        return R.ok();
    }

}
