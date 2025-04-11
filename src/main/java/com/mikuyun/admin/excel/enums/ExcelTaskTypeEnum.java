package com.mikuyun.admin.excel.enums;

import com.mikuyun.admin.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/4/11 20:38
 */
@Getter
@AllArgsConstructor
public enum ExcelTaskTypeEnum {

    EXAMPLE(0, "示例excel任务类型", ExcelQueueTypeEnum.NORMAL);

    private final Integer type;

    private final String desc;

    /**
     * 不同队列多线程并行任务数量详见{@link }
     */
    private final ExcelQueueTypeEnum queueEnum;

    private static final Map<Integer, ExcelTaskTypeEnum> EXCEL_TASK_MAP = new HashMap<>();

    static {
        for (ExcelTaskTypeEnum taskEnum : values()) {
            EXCEL_TASK_MAP.put(taskEnum.getType(), taskEnum);
        }
    }

    public static ExcelTaskTypeEnum getEnumByType(Integer type) {
        if (!EXCEL_TASK_MAP.containsKey(type)) {
            throw new BizException("无效导出类型");
        }
        return EXCEL_TASK_MAP.get(type);
    }

    /**
     * 导出任务投放队列类型枚举
     */
    public enum ExcelQueueTypeEnum {

        /**
         * 消费速率正常,不太消耗cpu的。
         */
        NORMAL,

        /**
         * 消费速率比较慢的,这种导出任务比较消耗cpu,需要排队慢慢执行
         */
        SLOW

    }

}
