package com.mikuyun.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author mikuyun
 * @since 2026/3/20 18:52
 */
@Data
public class ProhibitedWordsCheckDto {

    @NotBlank(message = "原始文本不能为空")
    private String text;

    /**
     * 违禁词替换字符
     */
    private String replacement;

    /**
     * 处理后文本
     */
    private String toText;

    /**
     * 文本是否含有违禁词
     */
    private Boolean prohibited;

}
