package com.mikuyun.admin.dto.dict;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author mikuyun
 * @since 2026/5/9 19:27
 */
@Data
public class EditDictDto {

    @Schema(description = "字典ID,编辑时传")
    private Integer id;

    @Schema(description = "关联sys_dict_type ID")
    @NotNull(message = "关联的字典类型不能为空")
    private Integer dictTypeId;

    @Schema(description = "字典枚举名称")
    @NotBlank(message = "字典枚举名称(显示名称)不能为空")
    private String enumName;

    @Schema(description = "字典枚举值")
    @NotBlank(message = "字典枚举值不能为空")
    private String enumCode;

    @Schema(description = "排序(正序)")
    private Integer sort;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "是否锁定，锁定的属性无法在页面进行修改")
    @NotBlank(message = "锁定状态不能为空")
    private Integer isLock;

}
