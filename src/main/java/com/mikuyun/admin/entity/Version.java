package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 软件版本
 * </p>
 *
 * @author qiseyun
 * @since 2024-05-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "版本")
public class Version extends BaseEntity {

    @Schema(name = "主键,自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "版本")
    private String versionKey;

    @Schema(name = "版本备注")
    private String remark;

    @Schema(name = "版本号")
    private String versionNumber;

    @Schema(name = "下载路径")
    private String downloadPath;

}
