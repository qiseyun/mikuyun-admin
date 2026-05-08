package com.mikuyun.admin.dto.sysconfig;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author mikuyun
 * @since 2026/2/8
 */
@Data
public class AddSysConfigDto {

    @Schema(name = "参数名")
    private String configName;

    @Schema(name = "参数键")
    private String configKey;

    @Schema(name = "参数值")
    private String configValue;

    @Schema(name = "是否锁定(0:否,1:是)")
    private Integer isLock;

    @Schema(name = "备注")
    private String remark;

}