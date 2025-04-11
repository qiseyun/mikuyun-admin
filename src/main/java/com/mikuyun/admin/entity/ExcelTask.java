package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/4/11 20:37
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mk_excel_task")
@Schema(description = "excel任务表")
@NoArgsConstructor
@AllArgsConstructor
public class ExcelTask extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "导出表格查询参数，json格式")
    private String param;

    @Schema(name = "文件名，为空时有默认")
    private String fileName;

    @Schema(name = "操作ip")
    private String operationIp;

    @Schema(name = "任务开始时间")
    private LocalDateTime taskStartTime;

    @Schema(name = "任务完成时间")
    private LocalDateTime taskFinishTime;

    @Schema(name = "0:示例excel导出")
    private Integer excelType;

    @Schema(name = "excel下载链接")
    private String downloadUrl;

    @Schema(name = "完成状态，1未完成 2已完成")
    private Integer status;

}
