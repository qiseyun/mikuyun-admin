package com.mikuyun.admin.vo.sysconfig;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author mikuyun
 * @version 1.0
 * @date 2026/2/8
 */
@Data
public class SysConfigListVo {

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

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreated;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

}