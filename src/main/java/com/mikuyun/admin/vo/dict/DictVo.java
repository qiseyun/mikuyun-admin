package com.mikuyun.admin.vo.dict;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mikuyun
 * @since 2026/5/8 21:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DictVo {

    /**
     * 字典ID
     */
    @Schema(description = "字典ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * dictTypeCode
     */
    private String dictTypeCode;

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
