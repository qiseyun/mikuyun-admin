package com.mikuyun.admin.excel.exampleexport;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/4/11 21:27
 */
@Data
public class ExampleExcelEntity {

    @ExcelProperty(value = "标题")
    private String exampleName = "示例导出";

}
