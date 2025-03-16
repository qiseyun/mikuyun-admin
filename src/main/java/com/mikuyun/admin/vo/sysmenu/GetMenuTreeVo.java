package com.mikuyun.admin.vo.sysmenu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2024年1月18日/0018 11点52分
 */
@Data
public class GetMenuTreeVo {


    /**
     * 菜单id
     */
    private Integer menuId;

    /**
     * 父菜单ID
     */
    private Integer parentId;

    /**
     * 菜单名称
     */
    @Schema(name = "菜单名称")
    private String name;

    /**
     * 子菜单
     */
    @Schema(name = "子菜单")
    private List<GetMenuTreeVo> children;

}
