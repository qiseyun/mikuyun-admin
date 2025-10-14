package com.mikuyun.admin.evt.DictType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/5/1 13:34
 */
@Data
public class DictTypeEvt {

    private Integer id;

    /**
     * 字典类型名(中文)
     */
    @Schema(description = "字典类型名(中文)")
    @NotBlank(message = "字典类型名不能为空")
    private String typeName;

    /**
     * 字典类型码,格式:表名(大驼峰)_字段名(小驼峰)
     */
    @Schema(description = "字典类型码,格式:表名(大驼峰)_字段名(小驼峰)")
    @NotBlank(message = "字典类型码不能为空")
    private String typeCode;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String remark;

    /**
     * 是否锁定，锁定的属性无法在页面进行修改
     */
    @Schema(description = "是否锁定，锁定的属性无法在页面进行修改")
    @NotNull(message = "锁定状态不能为空")
    private Integer isLock;

}
