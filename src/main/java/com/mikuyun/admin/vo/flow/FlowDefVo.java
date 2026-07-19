package com.mikuyun.admin.vo.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 流程定义响应
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@Data
public class FlowDefVo {

    @Schema(name = "流程定义ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(name = "流程编码")
    private String flowCode;

    @Schema(name = "流程名称")
    private String flowName;

    @Schema(name = "流程版本")
    private String version;

    @Schema(name = "发布状态 0-未发布 1-已发布")
    private Integer isPublish;

    @Schema(name = "激活状态 0-挂起 1-激活")
    private Integer isActive;

    @Schema(name = "来源路径")
    private String fromPath;

    @Schema(name = "监听器类型")
    private String listenerType;

    @Schema(name = "监听器路径")
    private String listenerPath;

    @Schema(name = "流程描述")
    private String description;

    @Schema(name = "创建人ID")
    private String createBy;

    @Schema(name = "创建人姓名")
    private String createByName;

    @Schema(name = "节点列表")
    private List<FlowNodeVo> nodeList;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
