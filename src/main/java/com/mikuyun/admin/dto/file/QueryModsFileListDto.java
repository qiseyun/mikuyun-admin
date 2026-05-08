package com.mikuyun.admin.dto.file;

import com.mikuyun.admin.dto.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author mikuyun
 * @since 2024年1月14日/0014 10点40分
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryModsFileListDto extends BasePageDto {


    @Schema(name = "文件id")
    private Integer fileId;

    @Schema(name = "文件类型（七日杀mod）")
    private String fileTypeStr;

    @Schema(name = "文件名称")
    private String originalName;

}
