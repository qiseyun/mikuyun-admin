package com.mikuyun.admin.excel;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.mikuyun.admin.entity.ExcelTask;
import com.mikuyun.admin.excel.engine.EasyExcelEngineServiceImpl;
import com.mikuyun.admin.excel.engine.ExcelExportEngineService;
import com.mikuyun.admin.excel.enums.ExcelEngineEnum;
import com.mikuyun.admin.exception.BizException;
import com.mikuyun.admin.service.IExcelTaskService;
import com.mikuyun.admin.service.minio.MinioService;
import com.mikuyun.admin.support.SpringContextUtils;
import io.minio.ObjectWriteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/4/11 21:36
 */
@Slf4j
@Component
public class ExcelTaskManager {

    private IBaseExcelTaskService baseExcelTaskService;

    private File outputFile;

    private String fileName;

    private String objectName;

    private JSONObject params;

    private ExcelTask excelTask;

    private final List<Object> dataList = new ArrayList<>();

    public ExcelTaskManager start(IBaseExcelTaskService taskService, ExcelTask excelTask) {
        this.baseExcelTaskService = taskService;
        this.excelTask = excelTask;
        String params = this.excelTask.getParam();
        this.params = JSONObject.parseObject(params);
        if (this.params != null) {
            this.params.put("downloadUserId", this.excelTask.getCreateBy());
        }
        this.fileName = taskService.getFileName(this.excelTask, this.params);
        if (StrUtil.isBlank(this.fileName)) {
            this.fileName = "defaultFile" + this.excelTask.getId();
        }
        String excelSuffix = ".xlsx";
        if (!this.fileName.endsWith(excelSuffix)) {
            this.fileName += excelSuffix;
        }
        try {
            String simpleUUID = IdUtil.simpleUUID();
            this.outputFile = File.createTempFile(simpleUUID, excelSuffix);
            this.objectName = "excel/" + LocalDate.now() + "/" + this.outputFile.getName();
        } catch (Exception e) {
            log.error("createTempFile error", e);
        }
        return this;
    }

    public void executionExport(Integer excelTaskId) {
        Integer pageNo = 0;
        try {
            int pageSize = 888;
            List<?> excelData = baseExcelTaskService.getPageData(this.excelTask, this.params, pageNo, pageSize);
            ExcelExportEngineService excelEngine = getExcelEngine();
            while (true) {
                if (CollectionUtil.isEmpty(excelData)) {
                    break;
                }
                dataList.addAll(excelData);
                if (dataList.size() >= pageSize) {
                    excelEngine.export(dataList);
                    dataList.clear();
                }
                if (excelData.size() < pageSize) {
                    break;
                }
                pageNo++;
                excelData = baseExcelTaskService.getPageData(this.excelTask, this.params, pageNo, pageSize);
            }
            if (CollectionUtil.isNotEmpty(dataList)) {
                excelEngine.export(dataList);
                dataList.clear();
            }
            excelEngine.exportFinish();
            //上传文件
            MinioService minioService = SpringContextUtils.getBean(MinioService.class);
            ObjectWriteResponse response;
            try (InputStream inputStream = new FileInputStream(this.outputFile)) {
                response = minioService.uploadFile(this.objectName, inputStream);
            }
            // 删除文件
            this.outputFile.delete();
            // 更新任务状态
            IExcelTaskService excelTaskService = SpringContextUtils.getBean(IExcelTaskService.class);
            excelTask.setFileName(fileName);
            excelTask.setTaskFinishTime(LocalDateTime.now());
            excelTask.setStatus(2);
            excelTask.setDownloadUrl(minioService.getPublicObjectUrl(response.object()));
            excelTaskService.updateById(excelTask);
        } catch (Exception e) {
            log.error("downloadTask error excelTaskId={} errorMsg={} 错误堆栈:", excelTask.getId(), e.getMessage(), e);
        }

    }

    /**
     * 获取excel引擎
     *
     * @return ExcelExportEngineService
     */
    private ExcelExportEngineService getExcelEngine() {
        ExcelEngineEnum excelEngineType = baseExcelTaskService.getExcelEngine();
        switch (excelEngineType) {
            case EASY_EXCEL -> {
                return new EasyExcelEngineServiceImpl(this.outputFile, baseExcelTaskService);
            }
            default -> throw new BizException("Unexpected value: " + excelEngineType);
        }
    }

}
