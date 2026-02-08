package com.mikuyun.admin.vo.sysrole;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

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

    /**
     * 角色描述
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreated;

}
