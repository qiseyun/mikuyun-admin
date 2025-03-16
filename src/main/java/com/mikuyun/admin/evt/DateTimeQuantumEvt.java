package com.mikuyun.admin.evt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年4月16日/0016 0点06分
 */
@Data
public class DateTimeQuantumEvt {

    /**
     * 开始时间
     */
    @Schema(name = "开始时间")
    private LocalDateTime startDateTime;

    /**
     * 结束时间
     */
    @Schema(name = "结束时间")
    private LocalDateTime endDateTime;

    /**
     * 开始日期
     */
    @Schema(name = "开始日期")
    private LocalDateTime startDate;

    /**
     * 结束日期
     */
    @Schema(name = "结束日期")
    private LocalDateTime endDate;

}
