package com.mikuyun.admin.evt.file;

import com.mikuyun.admin.evt.BasePageEvt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2024年1月14日/0014 10点40分
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class QueryFilePageEvt extends BasePageEvt {


    @Schema(name = "文件id")
    private Integer id;

    @Schema(name = "文件MD5")
    private String md5;

    @Schema(name = "文件格式")
    private String fileExt;

    @Schema(name = "文件大小(查询输入单位MB)最小")
    private String fileSizeByteMin;
    @Schema(name = "文件大小(查询输入单位MB)最大")
    private String fileSizeByteMax;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreatedStart;
    private LocalDateTime gmtCreatedEnd;

}
