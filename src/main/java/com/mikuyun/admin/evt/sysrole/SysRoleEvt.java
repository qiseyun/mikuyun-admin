package com.mikuyun.admin.evt.sysrole;

import lombok.Data;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2026/2/8 20:51
 */
@Data
public class SysRoleEvt {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色code
     */
    private String roleCode;

}
