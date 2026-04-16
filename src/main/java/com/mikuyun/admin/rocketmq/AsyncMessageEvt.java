package com.mikuyun.admin.rocketmq;

import com.alibaba.fastjson2.JSONObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @auth mikuyun
 * @since 2026/3/31 21:35
 */
@Data
public class AsyncMessageEvt {

    @NotBlank(message = "消息类型不能为空")
    private String type;

    @NotNull(message = "消息体内容不能为空")
    private JSONObject content;

    /**
     * 延时消息延时等级
     * 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     */
    private RocketMqDelayTimeEnum delayTimeLevel;
}
