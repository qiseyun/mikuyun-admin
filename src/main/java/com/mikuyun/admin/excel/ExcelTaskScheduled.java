package com.mikuyun.admin.excel;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.mikuyun.admin.common.Constant;
import com.mikuyun.admin.entity.ExcelTask;
import com.mikuyun.admin.excel.enums.ExcelTaskTypeEnum;
import com.mikuyun.admin.service.IExcelTaskService;
import com.mikuyun.admin.support.factory.ExcelEngineFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/4/11 22:47
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ExcelTaskScheduled implements InitializingBean {

    private final StringRedisTemplate stringRedisTemplate;

    private final ExcelEngineFactory excelEngineFactory;

    private final IExcelTaskService excelTaskService;

    @Value("${excelTask.normalRate:5}")
    private Integer normalRate;

    @Value("${excelTask.slowRate:1}")
    private Integer slowRate;

    /**
     * 限制并行任务数量
     */
    private Semaphore normalRateLimiter;
    private Semaphore slowRateLimiter;

    @Override
    public void afterPropertiesSet() {
        normalRateLimiter = new Semaphore(normalRate);
        slowRateLimiter = new Semaphore(slowRate);
    }

    /**
     * 每10秒执行一次
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void normalExecute() {
        // execute normal excel export scan
        pullMessageAndConsumer(Constant.CacheConstants.NORMAL_EXCEL_TASK, normalRateLimiter);
    }

    /**
     * 每30秒执行一次
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void slowExecute() {
        // execute slow excel export scan
        pullMessageAndConsumer(Constant.CacheConstants.SLOW_EXCEL_TASK, slowRateLimiter);
    }

    /**
     * 拉取消费导出任务
     *
     * @param excelTaskKey excelTask队列key
     * @param rateLimiter  限流器
     */
    private void pullMessageAndConsumer(String excelTaskKey, Semaphore rateLimiter) {
        while (true) {
            // 未获取到限流器
            if (!rateLimiter.tryAcquire()) {
                log.info("excelTaskActuator is busy");
                return;
            }
            try {
                // 从队列拉取导出任务
                String data = stringRedisTemplate.opsForList().rightPop(excelTaskKey);
                if (StrUtil.isBlank(data)) {
                    rateLimiter.release();
                    return;
                }
                // 使用虚拟线程执行
                log.info("virtual thread submit excelTask={}", data);
                Thread.startVirtualThread(() -> startExport(data, rateLimiter));
            } catch (Exception e) {
                // 如果在 pop 或提交前出错，必须释放许可
                log.error("Failed to process task queue: {}", e.getMessage(), e);
                rateLimiter.release();
                return;
            }
        }
    }

    /**
     * 执行导出
     *
     * @param data        excel任务
     * @param rateLimiter 限流器
     */
    private void startExport(String data, Semaphore rateLimiter) {
        try {
            ExcelTask excelTask = JSONObject.parseObject(data, ExcelTask.class);
            ExcelDataSupplier service = excelEngineFactory.createService(excelTask.getExcelType());
            ExcelTaskManager start = new ExcelTaskManager().start(service, excelTask);
            excelTask.setTaskStartTime(LocalDateTime.now());
            excelTaskService.updateById(excelTask);
            ExcelTaskTypeEnum excelTaskTypeEnum = ExcelTaskTypeEnum.getEnumByType(excelTask.getExcelType());
            log.info("Handling excelTaskId={} excelType={} desc={}", excelTask.getId(), excelTaskTypeEnum.getType(), excelTaskTypeEnum.getDesc());
            start.executionExport();
            log.info("Completed excelTaskId={} excelType={} desc={}", excelTask.getId(), excelTaskTypeEnum.getType(), excelTaskTypeEnum.getDesc());
        } catch (Exception e) {
            log.error("Error handling task: {}", data, e);
        } finally {
            // 释放许可
            rateLimiter.release();
        }
    }


}
