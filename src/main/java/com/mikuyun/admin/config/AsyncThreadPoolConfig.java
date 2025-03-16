package com.mikuyun.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2024/8/24 10:12
 */
@EnableAsync
@Configuration
public class AsyncThreadPoolConfig {

    /**
     * 线程池任务执行器
     *
     * @return executor
     */
    @Bean
    public Executor asyncTaskExecutor() {
        // 创建线程池
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8); // 设置核心池大小
        executor.setMaxPoolSize(8); // 设置最大池大小，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setQueueCapacity(1000); // 设置队列容量
        executor.setKeepAliveSeconds(120); // 设置保持活动秒数，当超过了核心线程数之外的线程在空闲时间到达之后会被销毁
        executor.setThreadNamePrefix("satokenAsyncThread-"); // 设置线程名称前缀
        // 设置拒绝的执行处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

}
