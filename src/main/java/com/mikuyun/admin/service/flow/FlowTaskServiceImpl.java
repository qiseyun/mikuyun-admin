package com.mikuyun.admin.service.flow;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.mikuyun.admin.dto.flow.FlowActionDto;
import com.mikuyun.admin.dto.flow.FlowTaskPageDto;
import com.mikuyun.admin.vo.flow.FlowHisTaskVo;
import com.mikuyun.admin.vo.flow.FlowTaskVo;
import lombok.RequiredArgsConstructor;
import org.dromara.warm.flow.core.FlowEngine;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.entity.HisTask;
import org.dromara.warm.flow.core.entity.Task;
import org.dromara.warm.flow.core.enums.SkipType;
import org.dromara.warm.flow.core.service.HisTaskService;
import org.dromara.warm.flow.core.service.TaskService;
import org.dromara.warm.flow.core.utils.page.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程任务服务实现
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@RequiredArgsConstructor
@Service
public class FlowTaskServiceImpl implements IFlowTaskService {

    private final TaskService taskService;
    private final HisTaskService hisTaskService;

    @Override
    public List<FlowTaskVo> myTodoList(FlowTaskPageDto dto) {
        dto.initPageParamsNoRestrictions();
        Task query = FlowEngine.newTask();
        if (dto.getDefinitionId() != null) {
            query.setDefinitionId(dto.getDefinitionId());
        }
        if (dto.getInstanceId() != null) {
            query.setInstanceId(dto.getInstanceId());
        }
        if (StrUtil.isNotBlank(dto.getFlowStatus())) {
            query.setFlowStatus(dto.getFlowStatus());
        }
        Page<Task> page = Page.pageOf((int) dto.getCurrent(), (int) dto.getSize());
        page = taskService.orderByCreateTime().desc().page(query, page);
        return page.getList().stream()
                .map(task -> BeanUtil.copyProperties(task, FlowTaskVo.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<FlowHisTaskVo> myDoneList(FlowTaskPageDto dto) {
        dto.initPageParamsNoRestrictions();
        HisTask query = FlowEngine.newHisTask();
        if (dto.getDefinitionId() != null) {
            query.setDefinitionId(dto.getDefinitionId());
        }
        if (dto.getInstanceId() != null) {
            query.setInstanceId(dto.getInstanceId());
        }
        if (StrUtil.isNotBlank(dto.getFlowStatus())) {
            query.setFlowStatus(dto.getFlowStatus());
        }
        Page<HisTask> page = Page.pageOf((int) dto.getCurrent(), (int) dto.getSize());
        page = hisTaskService.orderByCreateTime().desc().page(query, page);
        return page.getList().stream()
                .map(hisTask -> BeanUtil.copyProperties(hisTask, FlowHisTaskVo.class))
                .collect(Collectors.toList());
    }

    @Override
    public FlowTaskVo getDetail(Long id) {
        Task task = taskService.getById(id);
        return BeanUtil.copyProperties(task, FlowTaskVo.class);
    }

    @Override
    public List<FlowHisTaskVo> hisTaskList(Long instanceId) {
        HisTask query = FlowEngine.newHisTask();
        query.setInstanceId(instanceId);
        // 按创建时间正序，展示完整的审批链路
        List<HisTask> list = hisTaskService.orderByAsc("create_time").list(query);
        return list.stream()
                .map(hisTask -> BeanUtil.copyProperties(hisTask, FlowHisTaskVo.class))
                .collect(Collectors.toList());
    }

    @Override
    public void pass(FlowActionDto dto) {
        FlowParams flowParams = buildFlowParams(dto)
                .skipType(SkipType.PASS.getKey());
        executeSkip(dto, flowParams);
    }

    @Override
    public void reject(FlowActionDto dto) {
        FlowParams flowParams = buildFlowParams(dto)
                .skipType(SkipType.REJECT.getKey());
        executeSkip(dto, flowParams);
    }

    @Override
    public void transfer(FlowActionDto dto) {
        if (dto.getTaskId() == null) {
            throw new IllegalArgumentException("转办操作需要提供taskId");
        }
        FlowParams flowParams = buildFlowParams(dto)
                .addHandlers(dto.getAddHandlers());
        taskService.transfer(dto.getTaskId(), flowParams);
    }

    @Override
    public void depute(FlowActionDto dto) {
        if (dto.getTaskId() == null) {
            throw new IllegalArgumentException("委派操作需要提供taskId");
        }
        FlowParams flowParams = buildFlowParams(dto)
                .addHandlers(dto.getAddHandlers());
        taskService.depute(dto.getTaskId(), flowParams);
    }

    @Override
    public void addSignature(FlowActionDto dto) {
        if (dto.getTaskId() == null) {
            throw new IllegalArgumentException("加签操作需要提供taskId");
        }
        FlowParams flowParams = buildFlowParams(dto)
                .addHandlers(dto.getAddHandlers());
        taskService.addSignature(dto.getTaskId(), flowParams);
    }

    @Override
    public void reductionSignature(FlowActionDto dto) {
        if (dto.getTaskId() == null) {
            throw new IllegalArgumentException("减签操作需要提供taskId");
        }
        FlowParams flowParams = buildFlowParams(dto)
                .reductionHandlers(dto.getReductionHandlers());
        taskService.reductionSignature(dto.getTaskId(), flowParams);
    }

    @Override
    public void termination(FlowActionDto dto) {
        FlowParams flowParams = buildFlowParams(dto);
        if (dto.getTaskId() != null) {
            taskService.termination(dto.getTaskId(), flowParams);
        } else if (dto.getInstanceId() != null) {
            taskService.terminationByInsId(dto.getInstanceId(), flowParams);
        } else {
            throw new IllegalArgumentException("终止操作需要提供taskId或instanceId");
        }
    }

    @Override
    public void revoke(FlowActionDto dto) {
        if (dto.getInstanceId() == null) {
            throw new IllegalArgumentException("撤销操作需要提供instanceId");
        }
        FlowParams flowParams = buildFlowParams(dto);
        taskService.revoke(dto.getInstanceId(), flowParams);
    }

    /**
     * 构建 FlowParams 公共参数
     */
    private FlowParams buildFlowParams(FlowActionDto dto) {
        FlowParams flowParams = FlowParams.build()
                .message(dto.getMessage())
                .variable(dto.getVariable());
        if (dto.getPermissionFlag() != null && !dto.getPermissionFlag().isEmpty()) {
            flowParams.permissionFlag(dto.getPermissionFlag());
        }
        if (StrUtil.isNotBlank(dto.getNodeCode())) {
            flowParams.nodeCode(dto.getNodeCode());
        }
        return flowParams;
    }

    /**
     * 执行流转操作（通过/驳回），支持 taskId 和 instanceId 两种方式
     */
    private void executeSkip(FlowActionDto dto, FlowParams flowParams) {
        if (dto.getTaskId() != null) {
            taskService.skip(dto.getTaskId(), flowParams);
        } else if (dto.getInstanceId() != null) {
            taskService.skipByInsId(dto.getInstanceId(), flowParams);
        } else {
            throw new IllegalArgumentException("审批操作需要提供taskId或instanceId");
        }
    }

}
