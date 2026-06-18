package com.mikuyun.admin.service.flow;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.mikuyun.admin.dto.flow.FlowInsPageDto;
import com.mikuyun.admin.dto.flow.StartFlowDto;
import com.mikuyun.admin.vo.flow.FlowInsVo;
import lombok.RequiredArgsConstructor;
import org.dromara.warm.flow.core.FlowEngine;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.service.InsService;
import org.dromara.warm.flow.core.utils.page.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程实例服务实现
 *
 * @author mikuyun
 * @since 2026/6/18
 */
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
        return page.getList().stream()
                .map(ins -> BeanUtil.copyProperties(ins, FlowInsVo.class))
                .collect(Collectors.toList());
    }

    @Override
    public FlowInsVo getDetail(Long id) {
        Instance instance = insService.getById(id);
        return BeanUtil.copyProperties(instance, FlowInsVo.class);
    }

    @Override
    public FlowInsVo start(StartFlowDto dto) {
        FlowParams flowParams = FlowParams.build()
                .flowCode(dto.getFlowCode())
                .variable(dto.getVariable())
                .message(dto.getMessage());
        if (dto.getPermissionFlag() != null && !dto.getPermissionFlag().isEmpty()) {
            flowParams.permissionFlag(dto.getPermissionFlag());
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

}
