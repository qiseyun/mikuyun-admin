package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.dto.syspermissions.EditRPDto;
import com.mikuyun.admin.service.SysRolePermissionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
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
 * @author mikuyun
 * @since 2022-11-05
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "角色权限关联")
@RequestMapping("/sysRP")
public class SysRolePermissionsController {

    private final SysRolePermissionsService sysRolePermissionsService;

    @SaCheckPermission(value = "system:role:permission")
    @PostMapping(value = "/edit")
    @Operation(summary = "角色权限编辑")
    public R<Void> addPermissionToRole(@RequestBody @Valid EditRPDto dto) {
        sysRolePermissionsService.editRolePermission(dto);
        return R.ok();
    }

}
