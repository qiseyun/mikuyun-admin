package com.mikuyun.admin.dto.sysuser;

import com.mikuyun.admin.dto.BasePageDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mikuyun
 * @since 2026/2/7 20:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysUserListDto extends BasePageDto {

    @Schema(name = "手机号")
    private String phone;

}
