package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
/**
 * <p>
 * 字典类型
 * </p>
 *
 * @author jiangQL
 * @since 2025-04-18 23:14
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
@TableName("mk_dict_type")
@Schema(name = "DictType", description = "字典类型")
public class DictType extends BaseEntity {

    /**
     * 字典类型ID
     */
    @Schema(description = "字典类型ID")
    @TableId(value = "id", type = IdType.AUTO)
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

    /**
     * 是否锁定，锁定的属性无法在页面进行修改
     */
    @Schema(description = "是否锁定，锁定的属性无法在页面进行修改")
    private Boolean isLock;

}
