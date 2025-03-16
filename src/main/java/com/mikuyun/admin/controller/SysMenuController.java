package com.mikuyun.admin.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.evt.sysmenu.AddMenuOrButtonEvt;
import com.mikuyun.admin.service.SysMenuService;
import com.mikuyun.admin.vo.sysmenu.GetMenuTreeVo;
import com.mikuyun.admin.vo.sysmenu.QueryMenuListVo;
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

    /**
     * 系统菜单查询
     *
     * @return List<QueryMenuListVo>
     */
    @PostMapping(value = "/getMenuList")
    @Operation(summary = "获取系统菜单", description = "主菜单传0; 获取下级就传id")
    public R<List<QueryMenuListVo>> getMenuList(@Valid IdEvt evt) {
        return R.ok(sysMenuService.queryMenuList(evt));
    }

    /**
     * 新增菜单或按钮
     */
    @SaCheckRole(value = "super_admin")
    @PostMapping(value = "/addMenuOrButton")
    @Operation(summary = "新增菜单或按钮")
    public R<Void> addMenuOrButton(@RequestBody @Valid AddMenuOrButtonEvt evt) {
        sysMenuService.addMenuOrButton(evt);
        return R.ok();
    }

    /**
     * 登录用户系统菜单查询
     *
     * @return 菜单树
     */
    @PostMapping(value = "/getMenuTree")
    @Operation(summary = "获取管理员的系统菜单")
    public R<List<GetMenuTreeVo>> getMenuTree() {
        return R.ok(sysMenuService.getMenuTree());
    }

}
