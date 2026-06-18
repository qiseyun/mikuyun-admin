package com.mikuyun.admin.controller.flow;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.dto.IdListDto;
import com.mikuyun.admin.dto.flow.FlowInsPageDto;
import com.mikuyun.admin.dto.flow.StartFlowDto;
import com.mikuyun.admin.service.flow.IFlowInsService;
import com.mikuyun.admin.vo.flow.FlowInsVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程实例管理
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@Tag(name = "流程实例管理")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowIns")
public class FlowInsController {

    private final IFlowInsService flowInsService;

    @SaCheckPermission(value = "system:flow:ins:list")
    @GetMapping(value = "/list")
    @Operation(summary = "流程实例分页列表")
    public R<List<FlowInsVo>> list(FlowInsPageDto dto) {
        return R.ok(flowInsService.pageList(dto));
    }

    @SaCheckPermission(value = "system:flow:ins:list")
    @GetMapping(value = "/detail/{id}")
    @Operation(summary = "流程实例详情")
    public R<FlowInsVo> detail(@PathVariable @NotNull(message = "流程实例ID不能为空") Long id) {
        return R.ok(flowInsService.getDetail(id));
    }

    @SaCheckPermission(value = "system:flow:ins:start")
    @PostMapping(value = "/start")
    @Operation(summary = "启动流程")
    public R<FlowInsVo> start(@RequestBody @Valid StartFlowDto dto) {
        return R.ok(flowInsService.start(dto));
    }

    @SaCheckPermission(value = "system:flow:ins:edit")
    @PostMapping(value = "/active/{id}")
    @Operation(summary = "激活流程实例")
    public R<Void> active(@PathVariable @NotNull(message = "流程实例ID不能为空") Long id) {
        flowInsService.active(id);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:ins:edit")
    @PostMapping(value = "/unActive/{id}")
    @Operation(summary = "挂起流程实例")
    public R<Void> unActive(@PathVariable @NotNull(message = "流程实例ID不能为空") Long id) {
        flowInsService.unActive(id);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:ins:delete")
    @PostMapping(value = "/del")
    @Operation(summary = "删除流程实例")
    public R<Void> del(@RequestBody IdListDto<Long> dto) {
        List<Long> ids = dto.getIdList().stream().toList();
        flowInsService.remove(ids);
        return R.ok();
    }

}
