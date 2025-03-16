package com.mikuyun.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2024年1月14日/0014 10点28分
 */
@Data
public class QiseFilePageListVo {


    @Schema(name = "id")
    private Integer id;

    @Schema(name = "原始名称")
    private String originalName;

    @Schema(name = "文件MD5")
    private String md5;

    @Schema(name = "文件格式")
    private String fileExt;

    @Schema(name = "文件大小(字节)")
    private String fileSizeByte;

    @Schema(name = "文件路径")
    private String path;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreated;

}
