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
import java.util.Objects;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/4/11 21:36
 */
@Slf4j
@Component
public class ExcelTaskManager {

    private ExcelDataSupplier excelDataSupplier;

    /**
     * excel文件
     */
    private File outputFile;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 存储路径和文件名(excel/yyyy-MM-dd/xxxx.xlsx)
     */
    private String objectName;

    /**
     * 导出的查询参数
     */
    private JSONObject params;

    /**
     * excel任务对象
     */
    private ExcelTask excelTask;

    private final List<Object> dataList = new ArrayList<>();

    public ExcelTaskManager start(ExcelDataSupplier excelDataSupplier, ExcelTask excelTask) {
        this.excelDataSupplier = excelDataSupplier;
        this.excelTask = excelTask;
        String params = this.excelTask.getParam();
        this.params = JSONObject.parseObject(params);
        if (this.params != null) {
            this.params.put("downloadUserId", this.excelTask.getCreateBy());
        }
        this.fileName = excelDataSupplier.getFileName(this.excelTask, this.params);
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
            this.objectName = "excel/" + LocalDate.now() + "/" + this.outputFile.getName() + "_" + IdUtil.simpleUUID();
        } catch (Exception e) {
            log.error("createTempFile error", e);
        }
        return this;
    }

    /**
     * 执行导出
     */
    public void executionExport() {
        Integer pageNo = 0;
        try {
            int pageSize = 500;
            List<?> excelData = excelDataSupplier.getPageData(this.excelTask, this.params, pageNo, pageSize);
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
                excelData = excelDataSupplier.getPageData(this.excelTask, this.params, pageNo, pageSize);
            }
            if (CollectionUtil.isNotEmpty(dataList)) {
                excelEngine.export(dataList);
                dataList.clear();
            }
            excelEngine.exportFinish();
            // 上传文件并获取文件路径（这里上传到minio）
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
     * 初始化excel导出引擎
     *
     * @return ExcelExportEngineService
     */
    private ExcelExportEngineService getExcelEngine() {
        ExcelEngineEnum excelEngineType = excelDataSupplier.getExcelEngine();
        if (Objects.requireNonNull(excelEngineType) == ExcelEngineEnum.EASY_EXCEL) {
            return new EasyExcelEngineServiceImpl(this.outputFile, excelDataSupplier);
        }
        throw new BizException("Unexpected value: " + excelEngineType);
    }

}
