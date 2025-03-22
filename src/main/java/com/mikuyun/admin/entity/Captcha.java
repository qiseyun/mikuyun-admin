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
 * 验证码表
 * </p>
 *
 * @author qiseyun
 * @since 2023-04-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mk_captcha")
@Schema(description = "验证码表")
public class Captcha {

    @Schema(name = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(name = "接收账号")
    private String account;

    @Schema(name = "验证码")
    private String captchaStr;

    @Schema(name = "验证码类型(1手机号验证码,2邮箱验证码)")
    private Integer captchaType;

    @Schema(name = "创建时间")
    private LocalDateTime gmtCreated;

    @Schema(name = "到期时间")
    private LocalDateTime expirationTime;


}
