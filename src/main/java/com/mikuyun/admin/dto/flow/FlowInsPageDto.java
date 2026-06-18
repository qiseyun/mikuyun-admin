package com.mikuyun.admin.dto.flow;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mikuyun.admin.dto.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程实例分页查询参数
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FlowInsPageDto extends BasePageDto {

    @Schema(name = "流程定义ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long definitionId;

    @Schema(name = "流程编码")
    private String flowCode;

    @Schema(name = "业务ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private String businessId;

    @Schema(name = "流程状态 toDo-待办 pass-已通过 reject-已驳回 complete-已完成 termination-已终止")
    private String flowStatus;

    @Schema(name = "发起人")
    private String createBy;

}
