package com.mikuyun.admin.evt;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2024/5/5 上午1:09
 */
@Data
public class LogicDelEvt {

    /**
     * id
     */
    @Schema(name = "id")
    @NotNull(message = "id不能为空白!")
    private Integer id;

    /**
     * status
     */
    @Schema(name = "逻辑删除状态（1删；0正常）")
    @NotNull(message = "逻辑删除状态不能为空!")
    private Integer logicDeleteStatus;

}
