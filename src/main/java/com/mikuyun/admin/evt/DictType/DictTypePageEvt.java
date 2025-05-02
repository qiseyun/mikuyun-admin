package com.mikuyun.admin.evt.DictType;

import com.mikuyun.admin.evt.BasePageEvt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/5/1 13:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DictTypePageEvt extends BasePageEvt {

    private Integer id;

    /**
     * 字典类型名(中文)
     */
    @Schema(description = "字典类型名(中文)")
    private String typeName;

    /**
     * 字典类型码,格式:表名(大驼峰)_字段名(小驼峰)
     */
    @Schema(description = "字典类型码,格式:表名(大驼峰)_字段名(小驼峰)")
    private String typeCode;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String remark;

}
