package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户角色表
 * </p>
 *
 * @author qiseyun
 * @since 2023-05-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("qise_sys_user_role")
@Schema(description = "用户角色表")
public class SysUserRole {

    @Schema(name = "用户ID")
    private Integer userId;

    @Schema(name = "角色ID")
    private Integer roleId;


}
