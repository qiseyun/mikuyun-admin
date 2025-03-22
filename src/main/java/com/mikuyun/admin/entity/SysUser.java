package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mk_sys_user")
@Schema(description = "用户表")
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends BaseEntity {

    @Schema(name = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "用户名")
    private String username;

    @Schema(name = "密码")
    private String password;

    @Schema(name = "真实姓名")
    private String realName;

    @Schema(name = "随机盐")
    private String salt;

    @Schema(name = "手机号")
    private String phone;

    @Schema(name = "头像")
    private String avatar;

    @Schema(name = "部门ID(0:未分配)")
    private Integer deptId;

    @Schema(name = "0-正常，9-锁定")
    private Integer lockFlag;

    @Schema(name = "邮箱")
    private String email;

    @Schema(name = "是否超级管理员(0否 1是)")
    private Integer isSuperAdmin;

}
