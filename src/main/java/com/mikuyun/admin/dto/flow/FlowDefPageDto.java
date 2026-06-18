package com.mikuyun.admin.dto.flow;

import com.mikuyun.admin.dto.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 流程定义分页查询参数
 *
 * @author mikuyun
 * @since 2026/6/18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class FlowDefPageDto extends BasePageDto {

    @Schema(name = "流程编码")
    private String flowCode;

    @Schema(name = "流程名称")
    private String flowName;

    @Schema(name = "发布状态 0-未发布 1-已发布")
    private Integer isPublish;

}
