package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 参数配置表
 * </p>
 *
 * @author mikuyun
 * @since 2026-02-08 17:07
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("mk_sys_config")
@Schema(name = "SysConfig", description = "参数配置表")
public class SysConfig extends BaseEntity {

    /**
     * 参数配置ID
     */
    @Schema(description = "参数配置ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 参数名
     */
    @Schema(description = "参数名")
    private String configName;

    /**
     * 参数key
     */
    @Schema(description = "参数key")
    private String configKey;

    /**
     * 参数value
     */
    @Schema(description = "参数value")
    private String configValue;

    /**
     * 是否锁定
     */
    @Schema(description = "是否锁定")
    private Integer isLock;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

}
