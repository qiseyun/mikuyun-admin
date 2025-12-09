package com.mikuyun.admin.job;

import cn.hutool.core.thread.NamedThreadFactory;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.mikuyun.admin.common.Constant;
import com.mikuyun.admin.entity.ExcelTask;
import com.mikuyun.admin.excel.ExcelDataSupplier;
import com.mikuyun.admin.excel.ExcelTaskManager;
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
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    private final ThreadPoolExecutor excelTaskThreadPool = new ThreadPoolExecutor(
            4,
            10,
            120L,
            TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new NamedThreadFactory("excelExportTaskThread_", false),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );


    @Override
    public void afterPropertiesSet() {
        normalRateLimiter = new Semaphore(normalRate);
        slowRateLimiter = new Semaphore(slowRate);
    }

    /**
     * 每20秒执行一次
     */
    @Scheduled(cron = "0/20 * * * * ?")
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
            if (!rateLimiter.tryAcquire()) {
                log.info("excelTaskActuator is busy");
                return;
            }
            String data;
            try {
                data = stringRedisTemplate.opsForList().rightPop(excelTaskKey);
                if (StrUtil.isBlank(data)) {
                    rateLimiter.release();
                    return;
                }
            } catch (Exception e) {
                log.error("excelTaskScheduled failed to pop data from Redis queue errorMsg={} 错误堆栈", e.getMessage(), e);
                rateLimiter.release();
                return;
            }
            String excelTaskJsonStr = data;
            excelTaskThreadPool.execute(() -> {
                try {
                    ExcelTask excelTask = JSONObject.parseObject(excelTaskJsonStr, ExcelTask.class);
                    ExcelDataSupplier service = excelEngineFactory.createService(excelTask.getExcelType());
                    // 开始
                    ExcelTaskManager start = new ExcelTaskManager().start(service, excelTask);
                    excelTask.setTaskStartTime(LocalDateTime.now());
                    // 修改excelTask信息
                    excelTaskService.updateById(excelTask);
                    log.info("excelTaskScheduled handle excelTaskId={} excelType={}", excelTask.getId(), excelTask.getExcelType());
                    // 执行导出
                    start.executionExport();
                    log.info("excelTaskScheduled handle end excelTaskId={} excelType={}", excelTask.getId(), excelTask.getExcelType());
                } catch (Exception e) {
                    log.error("excelTaskScheduled handle error task={} errorMsg={} 错误堆栈", excelTaskJsonStr, e.getMessage(), e);
                } finally {
                    rateLimiter.release();
                }
            });
        }
    }


}
