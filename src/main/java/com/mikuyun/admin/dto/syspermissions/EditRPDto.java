package com.mikuyun.admin.dto.syspermissions;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author mikuyun
 * @since 2024年1月17日/0017 22点09分
 */
@Data
public class EditRPDto {

    @Schema(name = "角色id")
    @NotNull
    private Integer roleId;

    @Schema(name = "权限id")
    @NotEmpty
    private List<Integer> ids;

}
