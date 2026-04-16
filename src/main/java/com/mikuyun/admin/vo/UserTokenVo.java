package com.mikuyun.admin.vo;

import lombok.Data;

/**
 * @author mikuyun
 * @since 2025/3/30 09:38
 */
@Data
public class UserTokenVo {

    private String accessToken;

    private Long expiresTime;

}
