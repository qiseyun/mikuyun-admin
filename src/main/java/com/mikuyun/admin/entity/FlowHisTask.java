package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 历史任务记录表
 * </p>
 *
 * @author mikuyun
 * @since 2026-05-31 14:39
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("flow_his_task")
@Schema(name = "FlowHisTask", description = "历史任务记录表")
public class FlowHisTask {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键id")
    private Long id;

    /**
     * 对应flow_definition表的id
     */
    @Schema(description = "对应flow_definition表的id")
    private Long definitionId;

    /**
     * 对应flow_instance表的id
     */
    @Schema(description = "对应flow_instance表的id")
    private Long instanceId;

    /**
     * 对应flow_task表的id
     */
    @Schema(description = "对应flow_task表的id")
    private Long taskId;

    /**
     * 开始节点编码
     */
    @Schema(description = "开始节点编码")
    private String nodeCode;

    /**
     * 开始节点名称
     */
    @Schema(description = "开始节点名称")
    private String nodeName;

    /**
     * 开始节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）
     */
    @Schema(description = "开始节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）")
    private Boolean nodeType;

    /**
     * 目标节点编码
     */
    @Schema(description = "目标节点编码")
    private String targetNodeCode;

    /**
     * 结束节点名称
     */
    @Schema(description = "结束节点名称")
    private String targetNodeName;

    /**
     * 审批人
     */
    @Schema(description = "审批人")
    private String approver;

    /**
     * 协作方式(1审批 2转办 3委派 4会签 5票签 6加签 7减签)
     */
    @Schema(description = "协作方式(1审批 2转办 3委派 4会签 5票签 6加签 7减签)")
    private Boolean cooperateType;

    /**
     * 协作人
     */
    @Schema(description = "协作人")
    private String collaborator;

    /**
     * 流转类型（PASS通过 REJECT退回 NONE无动作）
     */
    @Schema(description = "流转类型（PASS通过 REJECT退回 NONE无动作）")
    private String skipType;

    /**
     * 流程状态（0待提交 1审批中 2审批通过 4终止 5作废 6撤销 8已完成 9已退回 10失效 11拿回）
     */
    @Schema(description = "流程状态（0待提交 1审批中 2审批通过 4终止 5作废 6撤销 8已完成 9已退回 10失效 11拿回）")
    private String flowStatus;

    /**
     * 审批表单是否自定义（Y是 N否）
     */
    @Schema(description = "审批表单是否自定义（Y是 N否）")
    private String formCustom;

    /**
     * 审批表单路径
     */
    @Schema(description = "审批表单路径")
    private String formPath;

    /**
     * 审批意见
     */
    @Schema(description = "审批意见")
    private String message;

    /**
     * 任务变量
     */
    @Schema(description = "任务变量")
    private String variable;

    /**
     * 业务详情 存业务表对象json字符串
     */
    @Schema(description = "业务详情 存业务表对象json字符串")
    private String ext;

    /**
     * 任务开始时间
     */
    @Schema(description = "任务开始时间")
    private LocalDateTime createTime;

    /**
     * 审批完成时间
     */
    @Schema(description = "审批完成时间")
    private LocalDateTime updateTime;

    /**
     * 删除标志
     */
    @Schema(description = "删除标志")
    private String delFlag;

    /**
     * 租户id
     */
    @Schema(description = "租户id")
    private String tenantId;
}
