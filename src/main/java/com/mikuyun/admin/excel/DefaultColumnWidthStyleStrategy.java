package com.mikuyun.admin.excel;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import org.apache.poi.ss.usermodel.Cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/12/13 11:17
 * 默认的列宽样式策略
 */
public class DefaultColumnWidthStyleStrategy extends AbstractColumnWidthStyleStrategy {

    private final Map<Integer, Map<Integer, Integer>> widthCache = new HashMap<>();

    /**
     * 最大宽度
     */
    private static final Integer MAX_WIDTH = 255;

    /**
     *
     * @param writeSheetHolder 当前写入的 Sheet 上下文，可获取 Sheet 对象
     * @param list             当前行该单元格的数据（非表头时有效）
     * @param cell             Apache POI 的 Cell 对象，可获取列索引、值等
     * @param head             表头信息（如果有）
     * @param relativeRowIndex 相对行指数
     * @param isHead           是否是表头行
     */
    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> list, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        boolean needSetWidth = isHead || !CollectionUtil.isEmpty(list);
        if (needSetWidth) {
            // 按 Sheet 编号 分组缓存，支持多 Sheet 场景。
            Map<Integer, Integer> maxColumnWidthMap = widthCache.computeIfAbsent(writeSheetHolder.getSheetNo(), k -> new HashMap<>());
            // 计算当前单元格列宽
            Integer columnWidth = StyleUtils.columnWidthCalculation(list, cell, isHead);
            if (columnWidth >= 0) {
                if (columnWidth > MAX_WIDTH) columnWidth = MAX_WIDTH;
                Integer maxColumnWidth = maxColumnWidthMap.get(cell.getColumnIndex());
                if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                    maxColumnWidthMap.put(cell.getColumnIndex(), columnWidth);
                    // Apache POI 的 setColumnWidth 单位是 1/256 个字符宽度，所以乘以 256 转换。
                    writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth * 256);
                }
            }
        }
    }

}
