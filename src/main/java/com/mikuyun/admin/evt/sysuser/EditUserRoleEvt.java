package com.mikuyun.admin.evt.sysuser;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2026/2/11 18:11
 */
@Data
public class EditUserRoleEvt {

    /**
     * 后台用户id
     */
    @NotNull
    private Integer sysUserId;

    /**
     * 角色id列表
     */
    @NotEmpty
    private List<Integer> roleIds;

}
