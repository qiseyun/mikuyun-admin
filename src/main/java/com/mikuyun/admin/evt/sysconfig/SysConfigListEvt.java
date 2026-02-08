package com.mikuyun.admin.evt.sysconfig;

import com.mikuyun.admin.evt.BasePageEvt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mikuyun
 * @version 1.0
 * @date 2026/2/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfigListEvt extends BasePageEvt {

    @Schema(name = "参数名称")
    private String configName;

    @Schema(name = "参数键值")
    private String configKey;

}