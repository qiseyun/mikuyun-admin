package com.mikuyun.admin.evt.sysuser;

import com.mikuyun.admin.evt.BasePageEvt;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2026/2/7 20:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserListEvt extends BasePageEvt {

    @Schema(name = "手机号")
    private String phone;

}
