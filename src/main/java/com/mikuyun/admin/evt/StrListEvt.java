package com.mikuyun.admin.evt;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年4月16日/0016 0点05分
 */
@Data
public class StrListEvt {

    /**
     * 字符串list
     */
    @Schema(name = "字符串list")
    @NotBlank(message = "参数不能为空白!")
    private List<String> strList;

}
