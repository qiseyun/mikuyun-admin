package com.mikuyun.admin.vo;


import com.mikuyun.admin.model.TreeModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * 城市地区表
 * </p>
 *
 * @author pengcheng
 * @since 2021-09-26
 */
@Data
@Schema(description = "城市地区表")
public class RegionTreeVO implements TreeModel<RegionTreeVO> {

    @Schema(description = "主键,自增")
    private Long id;

    @Schema(description = "区域名称")
    private String name;

    @Schema(description = "父节点编号")
    private Long pid;

    @Schema(description = "邮编")
    private String zip;

    @Schema(description = "子")
    private List<RegionTreeVO> children;

    @Override
    public Long getDeep() {
        return 0L;
    }

    @Override
    public Long getSort() {
        return this.id;
    }

}
