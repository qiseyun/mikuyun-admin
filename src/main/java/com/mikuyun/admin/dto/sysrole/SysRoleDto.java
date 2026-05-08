package com.mikuyun.admin.dto.sysrole;

import com.mikuyun.admin.dto.BasePageDto;
import lombok.Data;

/**
 * @author mikuyun
 * @since 2026/2/8 20:51
 */
@Data
public class SysRoleDto extends BasePageDto {

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色code
     */
    private String roleCode;

}
