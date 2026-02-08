package com.mikuyun.admin.vo.sysuser;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2026/2/7 20:20
 */
@Data
public class SysUserListVo {

    private Integer id;

    @Schema(name = "用户名")
    private String username;

    @Schema(name = "真实姓名")
    private String realName;

    @Schema(name = "手机号")
    private String phone;

    @Schema(name = "头像")
    private String avatar;

    @Schema(name = "部门ID(0:未分配)")
    private Integer deptId;

    @Schema(name = "0-正常，1-锁定")
    private Integer lockFlag;

    @Schema(name = "邮箱")
    private String email;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreated;

}
