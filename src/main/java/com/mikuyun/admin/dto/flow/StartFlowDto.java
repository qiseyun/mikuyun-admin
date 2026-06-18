package com.mikuyun.admin.dto.flow;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 启动流程请求参数
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@Data
public class StartFlowDto {

    @Schema(name = "流程编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "流程编码不能为空")
    private String flowCode;

    @Schema(name = "业务ID（关联业务数据的主键）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "业务ID不能为空")
    @JsonSerialize(using = ToStringSerializer.class)
    private String businessId;

    @Schema(name = "流程变量")
    private Map<String, Object> variable;

    @Schema(name = "审批消息/备注")
    private String message;

    @Schema(name = "权限标识列表，如 ['role:1', 'user:2']")
    private List<String> permissionFlag;

}
