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
 * 流程用户表
 * </p>
 *
 * @author mikuyun
 * @since 2026-05-31 14:39
 */
@Getter
@Setter
@ToString
@TableName("flow_user")
@Accessors(chain = true)
@Schema(name = "FlowUser", description = "流程用户表")
public class FlowUser {

    /**
     * 主键id
     */
    @Schema(description = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 人员类型（1待办任务的审批人权限 2待办任务的转办人权限 3待办任务的委托人权限）
     */
    @Schema(description = "人员类型（1待办任务的审批人权限 2待办任务的转办人权限 3待办任务的委托人权限）")
    private String type;

    /**
     * 权限人
     */
    @Schema(description = "权限人")
    private String processedBy;

    /**
     * 任务表id
     */
    @Schema(description = "任务表id")
    private Long associated;

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
     * 创建人
     */
    @Schema(description = "创建人")
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
