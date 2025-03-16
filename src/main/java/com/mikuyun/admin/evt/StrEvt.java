package com.mikuyun.admin.evt;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年4月16日/0016 0点04分
 */
@Data
public class StrEvt {

    /**
     * 字符串关键字
     */
    @Schema(name = "字符串关键字")
    @NotBlank(message = "参数不能为空白!")
    private String str;

}
