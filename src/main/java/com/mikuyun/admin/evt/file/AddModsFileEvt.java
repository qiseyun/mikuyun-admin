package com.mikuyun.admin.evt.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2024/4/30 上午9:03
 */
@Data
public class AddModsFileEvt {

    @Schema(name = "原始名称")
    private String originalName;

    @Schema(name = "文件格式")
    private String fileExt;

    @Schema(name = "文件类型")
    private String fileTypeStr;

    @Schema(name = "文件大小(mb)")
    private Integer fileSizeMb;

    @Schema(name = "文件路径")
    private String path;

}
