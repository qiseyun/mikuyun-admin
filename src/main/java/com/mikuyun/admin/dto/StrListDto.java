package com.mikuyun.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @author mikuyun
 * @since 2023年4月16日/0016 0点05分
 */
@Data
public class StrListDto {

    /**
     * 字符串list
     */
    @Schema(name = "字符串list")
    @NotBlank(message = "参数不能为空白!")
    private List<String> strList;

}
