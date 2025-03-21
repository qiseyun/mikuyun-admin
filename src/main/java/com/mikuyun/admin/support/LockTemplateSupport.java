package com.mikuyun.admin.support;


import com.mikuyun.admin.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2024/11/9 20:58
 */
@Slf4j
@Component
@AllArgsConstructor
public class LockTemplateSupport {

    private final RedissonClient redissonClient;

    /**
     * redis锁模板
     *
     * @param key      redis锁key
     * @param expire   时间
     * @param timeUnit 时间单位
     * @param runnable 回调
     */
    public void rLock(String key, Integer expire, TimeUnit timeUnit, Runnable runnable) {
        // 是否已锁定
        boolean isLocked = redissonClient.getLock(key).isLocked();
        if (isLocked) {
            throw new BizException("请稍后再试");
        }
        // 创建锁
        RLock rLock = redissonClient.getLock(key);
        try {
            rLock.lock(expire, timeUnit);
            // 执行
            runnable.run();
        } catch (Exception e) {
            rLock.unlock();
        } finally {
            rLock.unlock();
        }

    }

}
