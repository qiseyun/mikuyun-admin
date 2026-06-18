package com.mikuyun.admin.service.flow;

import com.mikuyun.admin.dto.flow.FlowActionDto;
import com.mikuyun.admin.dto.flow.FlowTaskPageDto;
import com.mikuyun.admin.vo.flow.FlowHisTaskVo;
import com.mikuyun.admin.vo.flow.FlowTaskVo;

import java.util.List;

/**
 * 流程任务服务接口
 *
 * @author mikuyun
 * @since 2026/6/18
 */
public interface IFlowTaskService {

    /**
     * 查询我的待办任务
     */
    List<FlowTaskVo> myTodoList(FlowTaskPageDto dto);

    /**
     * 查询我的已办任务
     */
    List<FlowHisTaskVo> myDoneList(FlowTaskPageDto dto);

    /**
     * 获取任务详情
     */
    FlowTaskVo getDetail(Long id);

    /**
     * 查询审批历史记录
     */
    List<FlowHisTaskVo> hisTaskList(Long instanceId);

    /**
     * 通过
     */
    void pass(FlowActionDto dto);

    /**
     * 驳回
     */
    void reject(FlowActionDto dto);

    /**
     * 转办
     */
    void transfer(FlowActionDto dto);

    /**
     * 委派
     */
    void depute(FlowActionDto dto);

    /**
     * 加签
     */
    void addSignature(FlowActionDto dto);

    /**
     * 减签
     */
    void reductionSignature(FlowActionDto dto);

    /**
     * 终止流程
     */
    void termination(FlowActionDto dto);

    /**
     * 撤销流程
     */
    void revoke(FlowActionDto dto);

}
