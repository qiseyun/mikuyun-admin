package com.mikuyun.admin.controller.flow;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.dto.IdListDto;
import com.mikuyun.admin.dto.flow.FlowDefPageDto;
import com.mikuyun.admin.service.flow.IFlowDefService;
import com.mikuyun.admin.vo.flow.FlowDefVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程定义管理
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@Tag(name = "流程定义管理")
@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/flowDef")
public class FlowDefController {

    private final IFlowDefService flowDefService;

    @SaCheckPermission(value = "system:flow:def:list")
    @GetMapping(value = "/list")
    @Operation(summary = "流程定义分页列表")
    public R<List<FlowDefVo>> list(FlowDefPageDto dto) {
        return R.ok(flowDefService.pageList(dto));
    }

    @SaCheckPermission(value = "system:flow:def:list")
    @GetMapping(value = "/detail/{id}")
    @Operation(summary = "流程定义详情（含节点信息）")
    public R<FlowDefVo> detail(@PathVariable @NotNull(message = "流程定义ID不能为空") Long id) {
        return R.ok(flowDefService.getDetail(id));
    }

    @SaCheckPermission(value = "system:flow:def:list")
    @GetMapping(value = "/design/{id}")
    @Operation(summary = "获取流程设计数据（JSON）")
    public R<String> design(@PathVariable @NotNull(message = "流程定义ID不能为空") Long id) {
        return R.ok(flowDefService.getDesign(id));
    }

    @SaCheckPermission(value = "system:flow:def:edit")
    @PostMapping(value = "/publish")
    @Operation(summary = "发布流程定义")
    public R<Void> publish(@RequestParam @NotNull(message = "流程定义ID不能为空") Long id) {
        flowDefService.publish(id);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:def:edit")
    @PostMapping(value = "/unPublish")
    @Operation(summary = "取消发布流程定义")
    public R<Void> unPublish(@RequestParam @NotNull(message = "流程定义ID不能为空") Long id) {
        flowDefService.unPublish(id);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:def:edit")
    @PostMapping(value = "/active")
    @Operation(summary = "激活流程定义")
    public R<Void> active(@RequestParam @NotNull(message = "流程定义ID不能为空") Long id) {
        flowDefService.active(id);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:def:edit")
    @PostMapping(value = "/unActive")
    @Operation(summary = "挂起流程定义")
    public R<Void> unActive(@RequestParam @NotNull(message = "流程定义ID不能为空") Long id) {
        flowDefService.unActive(id);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:def:delete")
    @PostMapping(value = "/del")
    @Operation(summary = "删除流程定义")
    public R<Void> del(@RequestBody IdListDto<Long> dto) {
        // IdListDto 中的 idList 为 Integer 类型，需转换为 Long
        List<Long> ids = dto.getIdList().stream().toList();
        flowDefService.removeDef(ids);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:def:add")
    @PostMapping(value = "/copy")
    @Operation(summary = "复制流程定义")
    public R<Void> copy(@RequestParam @NotNull(message = "流程定义ID不能为空") Long id) {
        flowDefService.copyDef(id);
        return R.ok();
    }

}
