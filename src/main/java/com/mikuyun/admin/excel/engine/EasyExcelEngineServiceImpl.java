package com.mikuyun.admin.excel.engine;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.mikuyun.admin.excel.ExcelDataSupplier;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/4/11 21:15
 * 大批量分页流式导出
 */
@Slf4j
public class EasyExcelEngineServiceImpl implements ExcelExportEngineService {

    private final ExcelWriter excelWriter;

    private final WriteSheet sheet;

    private OutputStream os;

    public EasyExcelEngineServiceImpl(File file, ExcelDataSupplier service) {
        try {
            this.os = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            log.error("init EasyExcelServiceImpl error file not found");
        }
        this.excelWriter = EasyExcel
                .write(os, service.getExcelDataClass())
                .registerWriteHandler(service.columnWidthStyleStrategy())
                .excelType(ExcelTypeEnum.XLSX)
                .build();
        this.sheet = EasyExcel.writerSheet("sheet").build();
    }

    @Override
    public void export(Collection<?> excelData) {
        excelWriter.write(excelData, sheet);
    }

    @Override
    public void exportFinish() {
        excelWriter.finish();
    }

}
