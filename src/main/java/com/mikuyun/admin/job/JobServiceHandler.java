package com.mikuyun.admin.job;

import com.mikuyun.admin.util.DateTimeInitializeUtils;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2024年1月23日/0023 22点16分
 */
@Component
@Slf4j
@AllArgsConstructor
public class JobServiceHandler {

    @XxlJob("jobTestHandler")
    public void jobTest() {
        log.info("每小时执行-------------{}", LocalDateTime.now().format(DateTimeInitializeUtils.commonDateTime()));
        // 会保存到调度日志的执行记录里
        XxlJobHelper.handleSuccess(
                "定时任务执行成功;执行日期：" + LocalDate.now().format(DateTimeInitializeUtils.chineseDate())
        );
    }

}
