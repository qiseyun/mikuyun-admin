package com.mikuyun.admin.service.flow;

import cn.hutool.core.bean.BeanUtil;
import com.mikuyun.admin.dto.flow.FlowDefPageDto;
import com.mikuyun.admin.vo.flow.FlowDefVo;
import com.mikuyun.admin.vo.flow.FlowNodeVo;
import lombok.RequiredArgsConstructor;
import org.dromara.warm.flow.core.FlowEngine;
import org.dromara.warm.flow.core.entity.Definition;
import org.dromara.warm.flow.core.entity.Node;
import org.dromara.warm.flow.core.service.DefService;
import org.dromara.warm.flow.core.service.NodeService;
import org.dromara.warm.flow.core.utils.page.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程定义服务实现
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@RequiredArgsConstructor
@Service
public class FlowDefServiceImpl implements IFlowDefService {

    private final DefService defService;
    private final NodeService nodeService;

    @Override
    public List<FlowDefVo> pageList(FlowDefPageDto dto) {
        dto.initPageParamsNoRestrictions();
        Definition query = FlowEngine.newDef();
        if (dto.getFlowCode() != null && !dto.getFlowCode().isEmpty()) {
            query.setFlowCode(dto.getFlowCode());
        }
        if (dto.getFlowName() != null && !dto.getFlowName().isEmpty()) {
            query.setFlowName(dto.getFlowName());
        }
        if (dto.getIsPublish() != null) {
            query.setIsPublish(dto.getIsPublish());
        }
        Page<Definition> page = Page.pageOf((int) dto.getCurrent(), (int) dto.getSize());
        page = defService.orderByCreateTime().desc().page(query, page);
        return page.getList().stream()
                .map(def -> BeanUtil.copyProperties(def, FlowDefVo.class))
                .collect(Collectors.toList());
    }

    @Override
    public FlowDefVo getDetail(Long id) {
        Definition definition = defService.getById(id);
        FlowDefVo vo = BeanUtil.copyProperties(definition, FlowDefVo.class);
        // 查询节点信息
        List<Node> nodes = nodeService.getByDefId(id);
        if (nodes != null) {
            vo.setNodeList(nodes.stream()
                    .map(node -> BeanUtil.copyProperties(node, FlowNodeVo.class))
                    .collect(Collectors.toList()));
        }
        return vo;
    }

    @Override
    public String getDesign(Long id) {
        return defService.exportJson(id);
    }

    @Override
    public void publish(Long id) {
        defService.publish(id);
    }

    @Override
    public void unPublish(Long id) {
        defService.unPublish(id);
    }

    @Override
    public void active(Long id) {
        defService.active(id);
    }

    @Override
    public void unActive(Long id) {
        defService.unActive(id);
    }

    @Override
    public void removeDef(List<Long> ids) {
        defService.removeDef(ids);
    }

    @Override
    public void copyDef(Long id) {
        defService.copyDef(id);
    }

}
