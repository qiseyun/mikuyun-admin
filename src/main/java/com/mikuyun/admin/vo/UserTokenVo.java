package com.mikuyun.admin.vo;

import lombok.Data;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/3/30 09:38
 */
@Data
public class UserTokenVo {

    private String accessToken;

    private Long expiresTime;

}
