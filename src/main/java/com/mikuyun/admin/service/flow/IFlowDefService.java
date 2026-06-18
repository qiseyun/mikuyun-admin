package com.mikuyun.admin.service.flow;

import com.mikuyun.admin.dto.flow.FlowDefPageDto;
import com.mikuyun.admin.vo.flow.FlowDefVo;

import java.util.List;

/**
 * 流程定义服务接口
 *
 * @author mikuyun
 * @since 2026/6/18
 */
public interface IFlowDefService {

    /**
     * 分页查询流程定义列表
     */
    List<FlowDefVo> pageList(FlowDefPageDto dto);

    /**
     * 获取流程定义详情（含节点信息）
     */
    FlowDefVo getDetail(Long id);

    /**
     * 获取流程设计数据
     */
    String getDesign(Long id);

    /**
     * 发布流程定义
     */
    void publish(Long id);

    /**
     * 取消发布流程定义
     */
    void unPublish(Long id);

    /**
     * 激活流程定义
     */
    void active(Long id);

    /**
     * 挂起流程定义
     */
    void unActive(Long id);

    /**
     * 删除流程定义
     */
    void removeDef(List<Long> ids);

    /**
     * 复制流程定义
     */
    void copyDef(Long id);

}
