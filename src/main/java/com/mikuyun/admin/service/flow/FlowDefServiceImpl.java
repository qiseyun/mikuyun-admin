package com.mikuyun.admin.service.flow;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
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
import java.util.Map;
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
        if (StrUtil.isNotBlank(dto.getFlowCode())) {
            query.setFlowCode(dto.getFlowCode());
        }
        if (StrUtil.isNotBlank(dto.getFlowName())) {
            query.setFlowName(dto.getFlowName());
        }
        if (dto.getIsPublish() != null) {
            query.setIsPublish(dto.getIsPublish());
        }
        Page<Definition> page = Page.pageOf((int) dto.getCurrent(), (int) dto.getSize());
        page = defService.orderByCreateTime().desc().page(query, page);
        List<FlowDefVo> voList = page.getList().stream()
                .map(def -> BeanUtil.copyProperties(def, FlowDefVo.class))
                .collect(Collectors.toList());
        // 填充用户姓名
        fillUserNames(voList);
        return voList;
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

    /**
     * 批量填充流程定义列表中的用户姓名
     */
    private void fillUserNames(List<FlowDefVo> voList) {
        if (CollectionUtil.isEmpty(voList)) {
            return;
        }
        List<String> userIds = voList.stream()
                .map(FlowDefVo::getCreateBy)
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
