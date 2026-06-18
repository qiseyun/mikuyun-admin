package com.mikuyun.admin.controller.flow;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.mikuyun.admin.common.R;
import com.mikuyun.admin.dto.flow.FlowActionDto;
import com.mikuyun.admin.dto.flow.FlowTaskPageDto;
import com.mikuyun.admin.service.flow.IFlowTaskService;
import com.mikuyun.admin.vo.flow.FlowHisTaskVo;
import com.mikuyun.admin.vo.flow.FlowTaskVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程任务管理
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@Tag(name = "流程任务管理")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowTask")
public class FlowTaskController {

    private final IFlowTaskService flowTaskService;

    @SaCheckPermission(value = "system:flow:task:list")
    @GetMapping(value = "/myTodo")
    @Operation(summary = "我的待办任务列表")
    public R<List<FlowTaskVo>> myTodo(FlowTaskPageDto dto) {
        return R.ok(flowTaskService.myTodoList(dto));
    }

    @SaCheckPermission(value = "system:flow:task:list")
    @GetMapping(value = "/myDone")
    @Operation(summary = "我的已办任务列表")
    public R<List<FlowHisTaskVo>> myDone(FlowTaskPageDto dto) {
        return R.ok(flowTaskService.myDoneList(dto));
    }

    @SaCheckPermission(value = "system:flow:task:list")
    @GetMapping(value = "/detail/{id}")
    @Operation(summary = "任务详情")
    public R<FlowTaskVo> detail(@PathVariable @NotNull(message = "任务ID不能为空") Long id) {
        return R.ok(flowTaskService.getDetail(id));
    }

    @SaCheckPermission(value = "system:flow:task:list")
    @GetMapping(value = "/hisList/{instanceId}")
    @Operation(summary = "审批历史记录（按实例ID查询）")
    public R<List<FlowHisTaskVo>> hisList(@PathVariable @NotNull(message = "流程实例ID不能为空") Long instanceId) {
        return R.ok(flowTaskService.hisTaskList(instanceId));
    }

    @SaCheckPermission(value = "system:flow:task:approve")
    @PostMapping(value = "/pass")
    @Operation(summary = "审批通过")
    public R<Void> pass(@RequestBody @Valid FlowActionDto dto) {
        flowTaskService.pass(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:task:approve")
    @PostMapping(value = "/reject")
    @Operation(summary = "审批驳回")
    public R<Void> reject(@RequestBody @Valid FlowActionDto dto) {
        flowTaskService.reject(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:task:approve")
    @PostMapping(value = "/transfer")
    @Operation(summary = "转办")
    public R<Void> transfer(@RequestBody @Valid FlowActionDto dto) {
        flowTaskService.transfer(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:task:approve")
    @PostMapping(value = "/depute")
    @Operation(summary = "委派")
    public R<Void> depute(@RequestBody @Valid FlowActionDto dto) {
        flowTaskService.depute(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:task:approve")
    @PostMapping(value = "/addSignature")
    @Operation(summary = "加签")
    public R<Void> addSignature(@RequestBody @Valid FlowActionDto dto) {
        flowTaskService.addSignature(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:task:approve")
    @PostMapping(value = "/reductionSignature")
    @Operation(summary = "减签")
    public R<Void> reductionSignature(@RequestBody @Valid FlowActionDto dto) {
        flowTaskService.reductionSignature(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:task:approve")
    @PostMapping(value = "/termination")
    @Operation(summary = "终止流程")
    public R<Void> termination(@RequestBody @Valid FlowActionDto dto) {
        flowTaskService.termination(dto);
        return R.ok();
    }

    @SaCheckPermission(value = "system:flow:task:approve")
    @PostMapping(value = "/revoke")
    @Operation(summary = "撤销流程")
    public R<Void> revoke(@RequestBody @Valid FlowActionDto dto) {
        flowTaskService.revoke(dto);
        return R.ok();
    }

}
