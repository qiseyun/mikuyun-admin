package com.mikuyun.admin.service.flow;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.mikuyun.admin.dto.flow.FlowInsPageDto;
import com.mikuyun.admin.dto.flow.StartFlowDto;
import com.mikuyun.admin.vo.flow.FlowInsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.FlowEngine;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.service.InsService;
import org.dromara.warm.flow.core.utils.page.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 流程实例服务实现
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FlowInsServiceImpl implements IFlowInsService {

    private final InsService insService;

    @Override
    public List<FlowInsVo> pageList(FlowInsPageDto dto) {
        dto.initPageParamsNoRestrictions();
        Instance query = FlowEngine.newIns();
        if (dto.getDefinitionId() != null) {
            query.setDefinitionId(dto.getDefinitionId());
        }
        if (StrUtil.isNotBlank(dto.getBusinessId())) {
            query.setBusinessId(dto.getBusinessId());
        }
        if (StrUtil.isNotBlank(dto.getFlowStatus())) {
            query.setFlowStatus(dto.getFlowStatus());
        }
        if (StrUtil.isNotBlank(dto.getCreateBy())) {
            query.setCreateBy(dto.getCreateBy());
        }
        Page<Instance> page = Page.pageOf((int) dto.getCurrent(), (int) dto.getSize());
        page = insService.orderByCreateTime().desc().page(query, page);
        List<FlowInsVo> voList = page.getList().stream()
                .map(ins -> {
                    FlowInsVo flowInsVo = BeanUtil.copyProperties(ins, FlowInsVo.class);
                    flowInsVo.setFlowCode(JSONObject.parseObject(ins.getDefJson()).getString("flowCode"));
                    flowInsVo.setFlowName(JSONObject.parseObject(ins.getDefJson()).getString("flowName"));
                    return flowInsVo;
                })
                .collect(Collectors.toList());
        // 填充用户姓名
        fillUserNames(voList);
        return voList;
    }

    @Override
    public FlowInsVo getDetail(Long id) {
        Instance instance = insService.getById(id);
        FlowInsVo vo = BeanUtil.copyProperties(instance, FlowInsVo.class);
        // 填充用户姓名
        if (vo.getCreateBy() != null) {
            Map<String, String> nameMap = FlowUserContext.getUserNameMap(List.of(vo.getCreateBy()));
            vo.setCreateByName(nameMap.get(vo.getCreateBy()));
        }
        return vo;
    }

    @Override
    public FlowInsVo start(StartFlowDto dto) {
        // 使用 FlowUserContext 自动注入当前用户 handler + permissionFlag
        FlowParams flowParams = FlowUserContext.buildFlowParams(dto.getFlowCode())
                .variable(dto.getVariable())
                .message(dto.getMessage());
        // 合并用户手动传入的 permissionFlag（如果有）
        if (CollectionUtil.isNotEmpty(dto.getPermissionFlag())) {
            List<String> mergedFlags = new java.util.ArrayList<>(flowParams.getPermissionFlag());
            mergedFlags.addAll(dto.getPermissionFlag());
            flowParams.permissionFlag(mergedFlags);
        }
        Instance instance = insService.start(dto.getBusinessId(), flowParams);
        return BeanUtil.copyProperties(instance, FlowInsVo.class);
    }

    @Override
    public void active(Long id) {
        insService.active(id);
    }

    @Override
    public void unActive(Long id) {
        insService.unActive(id);
    }

    @Override
    public void remove(List<Long> ids) {
        insService.remove(ids);
    }

    /**
     * 批量填充流程实例列表中的用户姓名
     */
    private void fillUserNames(List<FlowInsVo> voList) {
        if (CollectionUtil.isEmpty(voList)) {
            return;
        }
        List<String> userIds = voList.stream()
                .map(FlowInsVo::getCreateBy)
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
