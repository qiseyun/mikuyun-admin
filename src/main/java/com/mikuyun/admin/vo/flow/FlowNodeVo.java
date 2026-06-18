package com.mikuyun.admin.vo.flow;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 流程节点响应
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@Data
public class FlowNodeVo {

    @Schema(name = "节点ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(name = "流程定义ID")
    private Long definitionId;

    @Schema(name = "节点编码")
    private String nodeCode;

    @Schema(name = "节点名称")
    private String nodeName;

    @Schema(name = "节点类型 0-开始 1-中间 2-结束")
    private Integer nodeType;

    @Schema(name = "权限标识")
    private String permissionFlag;

    @Schema(name = "节点坐标(前端定位用)")
    private String coordinate;

}
