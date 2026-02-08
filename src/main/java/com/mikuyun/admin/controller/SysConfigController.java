package com.mikuyun.admin.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.evt.IdEvt;
import com.mikuyun.admin.evt.sysconfig.AddSysConfigEvt;
import com.mikuyun.admin.evt.sysconfig.SysConfigListEvt;
import com.mikuyun.admin.evt.sysconfig.UpdateSysConfigEvt;
import com.mikuyun.admin.service.ISysConfigService;
import com.mikuyun.admin.vo.sysconfig.SysConfigListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 参数配置表 前端控制器
 * </p>
 *
 * @author mikuyun
 * @since 2026-02-08 17:07
 */
@Tag(name = "系统参数配置管理")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/sysConfig")
public class SysConfigController {

    private final ISysConfigService sysConfigService;

    @GetMapping(value = "/list")
    @Operation(summary = "系统参数配置列表")
    public R<List<SysConfigListVo>> list(SysConfigListEvt evt) {
        return R.ok(sysConfigService.getSysConfigList(evt));
    }

    @SaCheckRole("super_admin")
    @PostMapping(value = "/add")
    @Operation(summary = "新增系统参数配置")
    public R<Void> addSysConfig(@RequestBody @Valid AddSysConfigEvt evt) {
        sysConfigService.addSysConfig(evt);
        return R.ok();
    }

    @SaCheckRole("super_admin")
    @PostMapping(value = "/update")
    @Operation(summary = "编辑系统参数配置")
    public R<Void> updateSysConfig(@RequestBody @Valid UpdateSysConfigEvt evt) {
        sysConfigService.updateSysConfig(evt);
        return R.ok();
    }

    @SaCheckRole("super_admin")
    @PostMapping(value = "/del")
    @Operation(summary = "删除系统参数配置")
    public R<Void> delSysConfig(@RequestBody @Valid IdEvt evt) {
        sysConfigService.delSysConfig(evt);
        return R.ok();
    }
}
