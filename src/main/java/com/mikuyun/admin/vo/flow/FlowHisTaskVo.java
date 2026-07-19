package com.mikuyun.admin.vo.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 历史任务响应
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@Data
public class FlowHisTaskVo {

    @Schema(name = "历史任务ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(name = "流程定义ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long definitionId;

    @Schema(name = "流程实例ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long instanceId;

    @Schema(name = "关联任务ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskId;

    @Schema(name = "节点编码")
    private String nodeCode;

    @Schema(name = "节点名称")
    private String nodeName;

    @Schema(name = "节点类型 0-开始 1-中间 2-结束")
    private Integer nodeType;

    @Schema(name = "流程状态 toDo-待办 pass-已通过 reject-已驳回 complete-已完成 termination-已终止")
    private String flowStatus;

    @Schema(name = "办理人")
    private String handler;

    @Schema(name = "审批类型 PASS-通过 REJECT-驳回 TRANSFER-转办 DEPUTE-委派 ADDSIGNATURE-加签 REDUCTIONSIGNATURE-减签 TERMINATION-终止 REVOKE-撤销")
    private String skipType;

    @Schema(name = "审批意见")
    private String message;

    @Schema(name = "发起人ID")
    private String createBy;

    @Schema(name = "发起人姓名")
    private String createByName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
