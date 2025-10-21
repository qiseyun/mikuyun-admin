package com.mikuyun.admin.excel.exampleexport;

import com.alibaba.fastjson2.JSONObject;
import com.mikuyun.admin.entity.ExcelTask;
import com.mikuyun.admin.excel.IBaseExcelTaskService;
import com.mikuyun.admin.excel.enums.ExcelTaskTypeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/4/11 21:29
 */
@Service
@RequiredArgsConstructor
public class ExampleExportServiceImpl implements IBaseExcelTaskService {

    @Override
    public Class<?> getExcelBeanClass() {
        return ExampleExcelEntity.class;
    }

    @Override
    public ExcelTaskTypeEnum getExcelTaskType() {
        return ExcelTaskTypeEnum.EXAMPLE;
    }

    @Override
    public String getFileName(ExcelTask excelTask, JSONObject params) {
        return getExcelTaskType().getDesc();
    }

    @Override
    public List<?> getPageData(ExcelTask excelTask, JSONObject params, Integer pageNo, Integer pageSize) {
        ArrayList<ExampleExcelEntity> excelData = new ArrayList<>();
        excelData.add(new ExampleExcelEntity());
        return excelData;
    }

}
