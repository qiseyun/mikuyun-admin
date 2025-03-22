package com.mikuyun.admin.vo.sysrole;

import lombok.Data;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年5月6日/0006 23点50分
 */
@Data
public class QuerySysRoleListVo {


    private Integer id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色code
     */
    private String roleCode;

    /**
     * 角色描述
     */
    private String roleDesc;

}
