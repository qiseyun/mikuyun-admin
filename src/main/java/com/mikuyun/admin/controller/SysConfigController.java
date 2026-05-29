package com.mikuyun.admin.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.dto.IdDto;
import com.mikuyun.admin.dto.sysconfig.AddSysConfigDto;
import com.mikuyun.admin.dto.sysconfig.SysConfigListDto;
import com.mikuyun.admin.dto.sysconfig.UpdateSysConfigDto;
import com.mikuyun.admin.service.ISysConfigService;
import com.mikuyun.admin.vo.sysconfig.SysConfigListVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
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

    @SaCheckPermission("system:config:page_view")
    @GetMapping(value = "/list")
    @Operation(summary = "系统参数配置列表")
    public R<List<SysConfigListVo>> list(SysConfigListDto dto) {
        return R.ok(sysConfigService.getSysConfigList(dto));
    }

    @SaCheckPermission("system:config:add")
    @PostMapping(value = "/add")
    @Operation(summary = "新增系统参数配置")
    public R<Void> addSysConfig(@RequestBody @Valid AddSysConfigDto dto) {
        sysConfigService.addSysConfig(dto);
        return R.ok();
    }

    @SaCheckPermission("system:config:edit")
    @PostMapping(value = "/update")
    @Operation(summary = "编辑系统参数配置")
    public R<Void> updateSysConfig(@RequestBody @Valid UpdateSysConfigDto dto) {
        sysConfigService.updateSysConfig(dto);
        return R.ok();
    }

    @SaCheckPermission("system:config:delete")
    @PostMapping(value = "/del")
    @Operation(summary = "删除系统配置")
    public R<Void> delSysConfig(@RequestBody @Valid IdDto dto) {
        sysConfigService.delSysConfig(dto);
        return R.ok();
    }
}
