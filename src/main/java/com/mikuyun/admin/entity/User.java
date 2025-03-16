package com.mikuyun.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author qiseyun
 * @since 2022-11-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("qise_user")
@Schema(description = "用户表")
public class User {

    @Schema(name = "主键,自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "用户密码")
    private String password;

    @Schema(name = "用户手机号")
    private String telephone;

    @Schema(name = "用户昵称")
    private String nickname;

    @Schema(name = "头像")
    private String headPortrait;

    @Schema(name = "性别 1：男 2：女 0：未知")
    private Integer gender;

    @Schema(name = "省")
    private Integer provinceId;

    @Schema(name = "生日")
    private LocalDateTime birthday;

    @Schema(name = "0：正常 1：禁用")
    private Integer isForbid;

    @Schema(name = "0：正常 1：删除")
    private Integer isDelete;

    @Schema(name = "注册时间")
    private LocalDateTime gmtCreated;

    @Schema(name = "修改时间")
    private LocalDateTime gmtModified;

}
