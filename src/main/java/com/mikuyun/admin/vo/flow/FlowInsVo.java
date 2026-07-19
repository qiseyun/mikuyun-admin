package com.mikuyun.admin.vo.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 流程实例响应
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@Data
public class FlowInsVo {

    @Schema(name = "流程实例ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(name = "流程定义ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long definitionId;

    @Schema(name = "流程编码")
    private String flowCode;

    @Schema(name = "流程名称")
    private String flowName;

    @Schema(name = "业务ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String businessId;

    @Schema(name = "当前节点类型 0-开始 1-中间 2-结束")
    private Integer nodeType;

    @Schema(name = "当前节点编码")
    private String nodeCode;

    @Schema(name = "当前节点名称")
    private String nodeName;

    @Schema(name = "流程状态 toDo-待办 pass-已通过 reject-已驳回 complete-已完成 termination-已终止")
    private String flowStatus;

    @Schema(name = "发起人ID")
    private String createBy;

    @Schema(name = "发起人姓名")
    private String createByName;

    @Schema(name = "扩展字段")
    private String ext;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
