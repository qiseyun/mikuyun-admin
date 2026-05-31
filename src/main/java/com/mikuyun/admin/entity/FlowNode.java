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
 * 流程节点表
 * </p>
 *
 * @author mikuyun
 * @since 2026-05-31 14:39
 */
@Getter
@Setter
@ToString
@TableName("flow_node")
@Accessors(chain = true)
@Schema(name = "FlowNode", description = "流程节点表")
public class FlowNode {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键id")
    private Long id;

    /**
     * 节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）
     */
    @Schema(description = "节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）")
    private Boolean nodeType;

    /**
     * 流程定义id
     */
    @Schema(description = "流程定义id")
    private Long definitionId;

    /**
     * 流程节点编码
     */
    @Schema(description = "流程节点编码")
    private String nodeCode;

    /**
     * 流程节点名称
     */
    @Schema(description = "流程节点名称")
    private String nodeName;

    /**
     * 权限标识（权限类型:权限标识，可以多个，用@@隔开)
     */
    @Schema(description = "权限标识（权限类型:权限标识，可以多个，用@@隔开)")
    private String permissionFlag;

    /**
     * 流程签署比例值
     */
    @Schema(description = "流程签署比例值")
    private String nodeRatio;

    /**
     * 坐标
     */
    @Schema(description = "坐标")
    private String coordinate;

    /**
     * 任意结点跳转
     */
    @Schema(description = "任意结点跳转")
    private String anyNodeSkip;

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
     * 版本
     */
    @Schema(description = "版本")
    private String version;

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
     * 节点扩展属性
     */
    @Schema(description = "节点扩展属性")
    private String ext;

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
