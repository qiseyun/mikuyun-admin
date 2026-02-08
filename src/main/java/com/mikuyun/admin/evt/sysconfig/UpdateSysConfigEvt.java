package com.mikuyun.admin.evt.sysconfig;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author mikuyun
 * @version 1.0
 * @date 2026/2/8
 */
@Data
public class UpdateSysConfigEvt {

    @Schema(name = "参数配置ID")
    private Integer id;

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