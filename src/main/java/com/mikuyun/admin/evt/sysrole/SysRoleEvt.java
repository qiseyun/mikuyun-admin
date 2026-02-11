package com.mikuyun.admin.evt.sysrole;

import com.mikuyun.admin.evt.BasePageEvt;
import lombok.Data;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2026/2/8 20:51
 */
@Data
public class SysRoleEvt extends BasePageEvt {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色code
     */
    private String roleCode;

}
