package com.mikuyun.admin.dto.sysconfig;

import com.mikuyun.admin.dto.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mikuyun
 * @since 2026/2/8
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysConfigListDto extends BasePageDto {

    @Schema(name = "参数名称")
    private String configName;

    @Schema(name = "参数键值")
    private String configKey;

}