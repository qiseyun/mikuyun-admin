package com.mikuyun.admin.evt;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年4月16日/0016 0点05分
 */
@Data
public class IdEvt {

    /**
     * id
     */
    @Schema(name = "id")
    @NotNull(message = "id不能为空白!")
    private Integer id;

}
