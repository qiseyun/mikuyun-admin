package com.mikuyun.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author mikuyun
 * @since 2023年4月16日/0016 0点05分
 */
@Data
public class IdListDto {

    /**
     * id列表
     */
    @Schema(name = "id列表")
    @NotNull(message = "id不能为空白!")
    private List<Integer> idList;

}
