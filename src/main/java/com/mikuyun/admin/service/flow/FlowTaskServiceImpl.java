package com.mikuyun.admin.service.flow;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
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
import java.util.Map;
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
        // 核心改造：按当前用户过滤待办任务
        String currentUserId = FlowUserContext.getCurrentUserId();
        if (currentUserId != null) {
            query.setCreateBy(currentUserId);
        }
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
        List<FlowTaskVo> voList = page.getList().stream()
                .map(task -> BeanUtil.copyProperties(task, FlowTaskVo.class))
                .collect(Collectors.toList());
        // 填充用户姓名
        fillTaskUserNames(voList);
        return voList;
    }

    @Override
    public List<FlowHisTaskVo> myDoneList(FlowTaskPageDto dto) {
        dto.initPageParamsNoRestrictions();
        HisTask query = FlowEngine.newHisTask();
        // 核心改造：按当前用户过滤已办任务
        String currentUserId = FlowUserContext.getCurrentUserId();
        if (currentUserId != null) {
            query.setCreateBy(currentUserId);
        }
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
        List<FlowHisTaskVo> voList = page.getList().stream()
                .map(hisTask -> BeanUtil.copyProperties(hisTask, FlowHisTaskVo.class))
                .collect(Collectors.toList());
        // 填充用户姓名
        fillHisTaskUserNames(voList);
        return voList;
    }

    @Override
    public FlowTaskVo getDetail(Long id) {
        Task task = taskService.getById(id);
        FlowTaskVo vo = BeanUtil.copyProperties(task, FlowTaskVo.class);
        // 填充用户姓名
        if (vo.getCreateBy() != null) {
            Map<String, String> nameMap = FlowUserContext.getUserNameMap(List.of(vo.getCreateBy()));
            vo.setCreateByName(nameMap.get(vo.getCreateBy()));
        }
        return vo;
    }

    @Override
    public List<FlowHisTaskVo> hisTaskList(Long instanceId) {
        HisTask query = FlowEngine.newHisTask();
        query.setInstanceId(instanceId);
        // 按创建时间正序，展示完整的审批链路
        List<HisTask> list = hisTaskService.orderByAsc("create_time").list(query);
        List<FlowHisTaskVo> voList = list.stream()
                .map(hisTask -> BeanUtil.copyProperties(hisTask, FlowHisTaskVo.class))
                .collect(Collectors.toList());
        // 填充用户姓名
        fillHisTaskUserNames(voList);
        return voList;
    }

    @Override
    public void pass(FlowActionDto dto) {
        assertTaskExists(dto.getTaskId(), "审批通过");
        FlowParams flowParams = buildFlowParams(dto)
                .skipType(SkipType.PASS.getKey());
        executeSkip(dto, flowParams);
    }

    @Override
    public void reject(FlowActionDto dto) {
        assertTaskExists(dto.getTaskId(), "审批驳回");
        FlowParams flowParams = buildFlowParams(dto)
                .skipType(SkipType.REJECT.getKey());
        executeSkip(dto, flowParams);
    }

    @Override
    public void transfer(FlowActionDto dto) {
        if (dto.getTaskId() == null) {
            throw new IllegalArgumentException("转办操作需要提供taskId");
        }
        assertTaskExists(dto.getTaskId(), "转办");
        FlowParams flowParams = buildFlowParams(dto)
                .addHandlers(dto.getAddHandlers());
        taskService.transfer(dto.getTaskId(), flowParams);
    }

    @Override
    public void depute(FlowActionDto dto) {
        if (dto.getTaskId() == null) {
            throw new IllegalArgumentException("委派操作需要提供taskId");
        }
        assertTaskExists(dto.getTaskId(), "委派");
        FlowParams flowParams = buildFlowParams(dto)
                .addHandlers(dto.getAddHandlers());
        taskService.depute(dto.getTaskId(), flowParams);
    }

    @Override
    public void addSignature(FlowActionDto dto) {
        if (dto.getTaskId() == null) {
            throw new IllegalArgumentException("加签操作需要提供taskId");
        }
        assertTaskExists(dto.getTaskId(), "加签");
        FlowParams flowParams = buildFlowParams(dto)
                .addHandlers(dto.getAddHandlers());
        taskService.addSignature(dto.getTaskId(), flowParams);
    }

    @Override
    public void reductionSignature(FlowActionDto dto) {
        if (dto.getTaskId() == null) {
            throw new IllegalArgumentException("减签操作需要提供taskId");
        }
        assertTaskExists(dto.getTaskId(), "减签");
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
     * 构建 FlowParams，自动注入当前用户 handler 和 permissionFlag
     */
    private FlowParams buildFlowParams(FlowActionDto dto) {
        // 核心改造：使用 FlowUserContext 自动注入当前用户信息
        FlowParams flowParams = FlowUserContext.buildFlowParams()
                .message(dto.getMessage())
                .variable(dto.getVariable());
        if (dto.getPermissionFlag() != null && !dto.getPermissionFlag().isEmpty()) {
            // 合并用户手动传入的权限标识
            List<String> mergedFlags = new java.util.ArrayList<>(flowParams.getPermissionFlag());
            mergedFlags.addAll(dto.getPermissionFlag());
            flowParams.permissionFlag(mergedFlags);
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

    /**
     * 校验任务是否存在（操作权限由 Warm-Flow 引擎内部校验）
     */
    private void assertTaskExists(Long taskId, String actionName) {
        if (taskId == null) {
            return; // 按instanceId操作时不校验单个任务
        }
        Task task = taskService.getById(taskId);
        if (task == null) {
            throw new IllegalArgumentException("任务不存在: " + taskId);
        }
    }

    /**
     * 批量填充待办任务列表中的用户姓名
     */
    private void fillTaskUserNames(List<FlowTaskVo> voList) {
        if (CollectionUtil.isEmpty(voList)) {
            return;
        }
        List<String> userIds = voList.stream()
                .map(FlowTaskVo::getCreateBy)
                .filter(StrUtil::isNotBlank)
                .distinct()
                .collect(Collectors.toList());
        if (CollectionUtil.isEmpty(userIds)) {
            return;
        }
        Map<String, String> nameMap = FlowUserContext.getUserNameMap(userIds);
        voList.forEach(vo -> {
            if (vo.getCreateBy() != null) {
                vo.setCreateByName(nameMap.get(vo.getCreateBy()));
            }
        });
    }

    /**
     * 批量填充历史任务列表中的用户姓名
     */
    private void fillHisTaskUserNames(List<FlowHisTaskVo> voList) {
        if (CollectionUtil.isEmpty(voList)) {
            return;
        }
        List<String> userIds = voList.stream()
                .map(FlowHisTaskVo::getCreateBy)
                .filter(StrUtil::isNotBlank)
                .distinct()
                .collect(Collectors.toList());
        if (CollectionUtil.isEmpty(userIds)) {
            return;
        }
        Map<String, String> nameMap = FlowUserContext.getUserNameMap(userIds);
        voList.forEach(vo -> {
            if (vo.getCreateBy() != null) {
                vo.setCreateByName(nameMap.get(vo.getCreateBy()));
            }
        });
    }

}
