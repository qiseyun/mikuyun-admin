package com.mikuyun.admin.excel.engine;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.mikuyun.admin.excel.ExcelDataSupplier;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collection;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/4/11 21:15
 */
@Slf4j
public class HutoolExcelEngineServiceImpl implements ExcelExportEngineService {

    private final ExcelWriter excelWriter;

    public HutoolExcelEngineServiceImpl(File file, ExcelDataSupplier service) {
        this.excelWriter = ExcelUtil.getBigWriter(file);
    }

    @Override
    public void export(Collection<?> excelData) {
        excelWriter.write(excelData);
    }

    @Override
    public void exportFinish() {
        excelWriter.close();
    }

}
