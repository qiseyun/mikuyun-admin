package com.mikuyun.admin.excel.engine;

import java.util.Collection;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/4/11 21:14
 */
public interface ExcelExportEngineService {

    /**
     * 导出数据
     *
     * @param excelData excel数据
     */
    void export(Collection<?> excelData);

    /**
     * 导出完成
     */
    void exportFinish();

}
