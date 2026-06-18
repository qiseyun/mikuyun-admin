package com.mikuyun.admin.service.flow;

import com.mikuyun.admin.dto.flow.FlowInsPageDto;
import com.mikuyun.admin.dto.flow.StartFlowDto;
import com.mikuyun.admin.vo.flow.FlowInsVo;

import java.util.List;

/**
 * 流程实例服务接口
 *
 * @author mikuyun
 * @since 2026/6/18
 */
public interface IFlowInsService {

    /**
     * 分页查询流程实例列表
     */
    List<FlowInsVo> pageList(FlowInsPageDto dto);

    /**
     * 获取流程实例详情
     */
    FlowInsVo getDetail(Long id);

    /**
     * 启动流程
     */
    FlowInsVo start(StartFlowDto dto);

    /**
     * 激活流程实例
     */
    void active(Long id);

    /**
     * 挂起流程实例
     */
    void unActive(Long id);

    /**
     * 删除流程实例
     */
    void remove(List<Long> ids);

}
