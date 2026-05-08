package com.mikuyun.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author mikuyun
 * @since 2023年4月16日/0016 0点05分
 */
@Data
public class IdDto {

    /**
     * id
     */
    @Schema(name = "id")
    @NotNull(message = "id不能为空白!")
    private Integer id;

}
