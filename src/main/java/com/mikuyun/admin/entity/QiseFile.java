package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件表
 * </p>
 *
 * @author qiseyun
 * @since 2023-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Schema(description = "文件表")
@AllArgsConstructor
@NoArgsConstructor
public class QiseFile extends BaseEntity {

    @Schema(name = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "原始名称")
    private String originalName;

    @Schema(name = "文件类型")
    private String type;

    @Schema(name = "文件MD5")
    private String md5;

    @Schema(name = "文件格式")
    private String fileExt;

    @Schema(name = "文件大小(字节)")
    private String fileSizeByte;

    @Schema(name = "文件地址")
    private String url;

}
