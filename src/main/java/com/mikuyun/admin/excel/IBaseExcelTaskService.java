package com.mikuyun.admin.excel;

import com.alibaba.fastjson2.JSONObject;
import com.mikuyun.admin.entity.ExcelTask;
import com.mikuyun.admin.excel.enums.ExcelEngineEnum;
import com.mikuyun.admin.excel.enums.ExcelTaskTypeEnum;

import java.util.List;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/4/11 20:35
 */
public interface IBaseExcelTaskService {

    /**
     * 获取excel模板类
     *
     * @return Class<T>
     */
    Class<?> getExcelBeanClass();

    /**
     * 获取任务类型
     *
     * @return ExcelTaskTypeEnum
     */
    ExcelTaskTypeEnum getExcelTaskType();

    /**
     * 获取文件名
     *
     * @param excelTask excel任务
     * @param params    参数
     * @return String
     */
    String getFileName(ExcelTask excelTask, JSONObject params);


    /**
     * 分页获取数据
     *
     * @param excelTask excel任务
     * @param params    参数
     * @param pageNo    页码
     * @param pageSize  每页大小
     * @return List<T>
     */
    List<?> getPageData(ExcelTask excelTask, JSONObject params, Integer pageNo, Integer pageSize);

    /**
     * 默认导出引擎
     *
     * @return ExcelEngineEnum
     */
    default ExcelEngineEnum getExcelEngine() {
        return ExcelEngineEnum.EASY_EXCEL;
    }

}
