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
 * 节点跳转关联表
 * </p>
 *
 * @author mikuyun
 * @since 2026-05-31 14:39
 */
@Getter
@Setter
@ToString
@TableName("flow_skip")
@Accessors(chain = true)
@Schema(name = "FlowSkip", description = "节点跳转关联表")
public class FlowSkip {

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 流程定义id
     */
    @Schema(description = "流程定义id")
    private Long definitionId;

    /**
     * 当前流程节点的编码
     */
    @Schema(description = "当前流程节点的编码")
    private String nowNodeCode;

    /**
     * 当前节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）
     */
    @Schema(description = "当前节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）")
    private Boolean nowNodeType;

    /**
     * 下一个流程节点的编码
     */
    @Schema(description = "下一个流程节点的编码")
    private String nextNodeCode;

    /**
     * 下一个节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）
     */
    @Schema(description = "下一个节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）")
    private Boolean nextNodeType;

    /**
     * 跳转名称
     */
    @Schema(description = "跳转名称")
    private String skipName;

    /**
     * 跳转类型（PASS审批通过 REJECT退回）
     */
    @Schema(description = "跳转类型（PASS审批通过 REJECT退回）")
    private String skipType;

    /**
     * 跳转条件
     */
    @Schema(description = "跳转条件")
    private String skipCondition;

    /**
     * 坐标
     */
    @Schema(description = "坐标")
    private String coordinate;

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
