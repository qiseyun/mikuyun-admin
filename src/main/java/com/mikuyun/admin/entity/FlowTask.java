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
 * 待办任务表
 * </p>
 *
 * @author mikuyun
 * @since 2026-05-31 14:39
 */
@Getter
@Setter
@ToString
@TableName("flow_task")
@Accessors(chain = true)
@Schema(name = "FlowTask", description = "待办任务表")
public class FlowTask {

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
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
     * 节点编码
     */
    @Schema(description = "节点编码")
    private String nodeCode;

    /**
     * 节点名称
     */
    @Schema(description = "节点名称")
    private String nodeName;

    /**
     * 节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）
     */
    @Schema(description = "节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）")
    private Boolean nodeType;

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
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private String createBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @Schema(description = "更新人")
    private String updateBy;

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
