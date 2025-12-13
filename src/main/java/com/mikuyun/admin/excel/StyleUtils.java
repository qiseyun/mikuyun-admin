package com.mikuyun.admin.excel;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/12/13 11:40
 */
public class StyleUtils {

    /**
     * 内容宽度估算
     *
     * @param list   当前行该单元格的数据（非表头时有效）
     * @param cell   Apache POI 的 Cell 对象，可获取列索引、值等
     * @param isHead 是否是表头行
     * @return 列宽
     */
    public static Integer columnWidthCalculation(List<WriteCellData<?>> list, Cell cell, Boolean isHead) {
        // 直接取表头文本的 字节长度
        if (isHead) {
            return cell.getStringCellValue().getBytes().length;
        } else {
            WriteCellData<?> cellData = list.getFirst();
            CellDataTypeEnum type = cellData.getType();
            if (type == null) {
                return -1;
            } else {
                return switch (type) {
                    case STRING -> cellData.getStringValue().getBytes().length + 10;
                    case BOOLEAN -> cellData.getBooleanValue().toString().getBytes().length;
                    case NUMBER -> cellData.getNumberValue().toString().getBytes().length + 10;
                    default -> -1;
                };
            }
        }
    }

}
