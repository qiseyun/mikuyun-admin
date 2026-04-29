package com.mikuyun.admin.rocketmq.enums;

import com.mikuyun.admin.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * @author mikuyun
 * @since 2026/4/4 13:11
 * RocketMQ 延迟消息等级枚举（按延迟时间命名）
 * 对应默认配置: "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h"
 */
@Getter
@AllArgsConstructor
public enum RocketMqDelayTimeEnum {

    S_1(1, 1, TimeUnit.SECONDS),
    S_5(2, 5, TimeUnit.SECONDS),
    S_10(3, 10, TimeUnit.SECONDS),
    S_30(4, 30, TimeUnit.SECONDS),
    M_1(5, 1, TimeUnit.MINUTES),
    M_2(6, 2, TimeUnit.MINUTES),
    M_3(7, 3, TimeUnit.MINUTES),
    M_4(8, 4, TimeUnit.MINUTES),
    M_5(9, 5, TimeUnit.MINUTES),
    M_6(10, 6, TimeUnit.MINUTES),
    M_7(11, 7, TimeUnit.MINUTES),
    M_8(12, 8, TimeUnit.MINUTES),
    M_9(13, 9, TimeUnit.MINUTES),
    M_10(14, 10, TimeUnit.MINUTES),
    M_20(15, 20, TimeUnit.MINUTES),
    M_30(16, 30, TimeUnit.MINUTES),
    H_1(17, 1, TimeUnit.HOURS),
    H_2(18, 2, TimeUnit.HOURS);

    private final int level;

    private final int value;

    private final TimeUnit unit;

    public long toMillis() {
        return unit.toMillis(value);
    }

    public long toSeconds() {
        return unit.toSeconds(value);
    }

    /**
     * 根据 level (1-18) 获取对应的枚举
     */
    public static RocketMqDelayTimeEnum fromLevel(int level) {
        for (RocketMqDelayTimeEnum item : values()) {
            if (item.level == level) {
                return item;
            }
        }
        throw new BizException("Invalid RocketMQ delay level: " + level + " (valid: 1-18)");
    }

    /**
     * 根据目标延迟秒数，选择最接近且不小于该时间的最小延迟等级
     */
    public static RocketMqDelayTimeEnum chooseBestForAtLeast(long targetSeconds) {
        RocketMqDelayTimeEnum best = null;
        for (RocketMqDelayTimeEnum candidate : values()) {
            if (candidate.toSeconds() >= targetSeconds) {
                if (best == null || candidate.toSeconds() < best.toSeconds()) {
                    best = candidate;
                }
            }
        }
        // 超出范围则返回最大（2小时）
        return best != null ? best : H_2;
    }

    @Override
    public String toString() {
        String unitStr = unit == TimeUnit.SECONDS ? "s" :
                unit == TimeUnit.MINUTES ? "m" : "h";
        return value + unitStr + "(level=" + level + ")";
    }

}
