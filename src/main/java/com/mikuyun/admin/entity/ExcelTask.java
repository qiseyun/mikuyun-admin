package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mikuyun.admin.excel.enums.ExcelTaskTypeEnum;
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
@Schema(description = "excel 导出任务表")
@NoArgsConstructor
@AllArgsConstructor
public class ExcelTask extends BaseEntity {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "导出表格查询参数，json格式")
    private String param;

    @Schema(name = "文件名，为空时有默认")
    private String fileName;

    @Schema(name = "操作 ip")
    private String operationIp;

    @Schema(name = "任务开始时间")
    private LocalDateTime taskStartTime;

    @Schema(name = "任务完成时间")
    private LocalDateTime taskFinishTime;

    /**
     * {@link ExcelTaskTypeEnum}
     */
    @Schema(name = "导出任务类型,见枚举:ExcelTaskTypeEnum")
    private Integer excelType;

    @Schema(name = "下载链接")
    private String downloadUrl;

    @Schema(name = "完成状:0未完成 1未完成 2已完成")
    private Integer status;

}
