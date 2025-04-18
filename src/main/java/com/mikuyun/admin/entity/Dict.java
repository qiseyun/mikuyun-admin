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
 * 字典表
 * </p>
 *
 * @author jiangQL
 * @since 2025-04-18 23:14
 */
@Getter
@Setter
@ToString
@TableName("mk_dict")
@Accessors(chain = true)
@Schema(name = "Dict", description = "字典表")
public class Dict extends BaseEntity {

    /**
     * 字典ID
     */
    @Schema(description = "字典ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联sys_dict_type ID
     */
    @Schema(description = "关联sys_dict_type ID")
    private Integer sysDictTypeId;

    /**
     * 字典枚举名称
     */
    @Schema(description = "字典枚举名称")
    private String enumName;

    /**
     * 字典枚举值
     */
    @Schema(description = "字典枚举值")
    private String enumCode;

    /**
     * 排序(正序)
     */
    @Schema(description = "排序(正序)")
    private Integer sort;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 是否锁定，锁定的属性无法在页面进行修改
     */
    @Schema(description = "是否锁定，锁定的属性无法在页面进行修改")
    private Boolean isLock;

}
