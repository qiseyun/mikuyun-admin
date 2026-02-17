package com.mikuyun.admin.vo.syspermissions;

import com.mikuyun.admin.model.TreeModel;
import lombok.Data;

import java.util.List;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2024年1月18日/0018 11点52分
 */
@Data
public class SysPermissionListVo implements TreeModel<SysPermissionListVo> {

    /**
     * 菜单id
     */
    private Long id;

    /**
     * 父菜单ID
     */
    private Long pid;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * permission
     */
    private String permission;

    /**
     * 0-开启，1-关闭
     */
    private Integer keepAlive;

    /**
     * 菜单类型 （0:页面 1:组件 2:接口）
     */
    private Integer type;

    private List<SysPermissionListVo> children;

    @Override
    public Long getDeep() {
        return 0L;
    }

    @Override
    public Long getSort() {
        return this.id;
    }

}
