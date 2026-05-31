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
 * 流程定义表
 * </p>
 *
 * @author mikuyun
 * @since 2026-05-31 14:39
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("flow_definition")
@Schema(name = "FlowDefinition", description = "流程定义表")
public class FlowDefinition {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键id")
    private Long id;

    /**
     * 流程编码
     */
    @Schema(description = "流程编码")
    private String flowCode;

    /**
     * 流程名称
     */
    @Schema(description = "流程名称")
    private String flowName;

    /**
     * 设计器模型（CLASSICS经典模型 MIMIC仿钉钉模型）
     */
    @Schema(description = "设计器模型（CLASSICS经典模型 MIMIC仿钉钉模型）")
    private String modelValue;

    /**
     * 流程类别
     */
    @Schema(description = "流程类别")
    private String category;

    /**
     * 流程版本
     */
    @Schema(description = "流程版本")
    private String version;

    /**
     * 是否发布（0未发布 1已发布 9失效）
     */
    @Schema(description = "是否发布（0未发布 1已发布 9失效）")
    private Boolean isPublish;

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
     * 流程激活状态（0挂起 1激活）
     */
    @Schema(description = "流程激活状态（0挂起 1激活）")
    private Boolean activityStatus;

    /**
     * 监听器类型
     */
    @Schema(description = "监听器类型")
    private String listenerType;

    /**
     * 监听器路径
     */
    @Schema(description = "监听器路径")
    private String listenerPath;

    /**
     * 业务详情 存业务表对象json字符串
     */
    @Schema(description = "业务详情 存业务表对象json字符串")
    private String ext;

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
