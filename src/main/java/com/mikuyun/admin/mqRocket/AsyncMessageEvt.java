package com.mikuyun.admin.mqRocket;

import com.alibaba.fastjson2.JSONObject;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @auth mikuyun
 * @date 2026/3/31 21:35
 */
@Data
public class AsyncMessageEvt {

    @NotBlank(message = "消息类型不能为空")
    private String type;

    @NotNull(message = "消息体内容不能为空")
    private JSONObject content;

    /**
     * 延时消息延时秒数
     */
    private Long delayTimeSec;
}
