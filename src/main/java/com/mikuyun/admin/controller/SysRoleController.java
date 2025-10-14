package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.sysrole.AddSysRoleListEvt;
import com.mikuyun.admin.service.SysRoleService;
import com.mikuyun.admin.vo.sysrole.QuerySysRoleListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 系统角色列表查询
     *
     * @return List<QuerySysRoleListVo>
     */
    @PostMapping(value = "/getRoleList")
    @Operation(summary = "获取系统角色列表")
    public R<List<QuerySysRoleListVo>> getRoleList() {
        return R.ok(sysRoleService.queryRoleList());
    }

    /**
     * 新增菜单或按钮
     */
    @SaCheckRole(value = "admin")
    @PostMapping(value = "/addSysRole")
    @Operation(summary = "新增角色")
    public R<Void> addSysRole(@RequestBody @Valid AddSysRoleListEvt evt) {
        sysRoleService.addSysRole(evt);
        return R.ok();
    }

}
