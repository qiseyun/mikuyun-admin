package com.mikuyun.admin.dto.flow;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 流程审批操作通用请求参数
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@Data
public class FlowActionDto {

    @Schema(name = "任务ID（与instanceId二选一）")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long taskId;

    @Schema(name = "流程实例ID（与taskId二选一）")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long instanceId;

    @Schema(name = "目标节点编码（跳转到指定节点时使用）")
    private String nodeCode;

    @Schema(name = "流转类型（无需传入，由具体操作接口自动确定：pass→PASS, reject→REJECT, transfer→TRANSFER, depute→DEPUTE, addSignature→ADDSIGNATURE 等）")
    private String skipType;

    @Schema(name = "审批消息/备注")
    private String message;

    @Schema(name = "流程变量")
    private Map<String, Object> variable;

    @Schema(name = "权限标识列表，如 ['role:1', 'role:2']")
    private List<String> permissionFlag;

    @Schema(name = "新增办理人列表（转办/委派/加签时使用）")
    private List<String> addHandlers;

    @Schema(name = "减少办理人列表（减签时使用）")
    private List<String> reductionHandlers;

}
