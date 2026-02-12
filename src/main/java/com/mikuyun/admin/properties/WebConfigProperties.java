package com.mikuyun.admin.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author qiseyun
 * @version 0.0.1
 * @date 2021/11/25 - 11:59
 */
@Data
@Component
public class WebConfigProperties {

    @Value("${mikuyun.salt.admin}")
    private String salt;

    @Value("${mikuyun.salt..user}")
    private String userSalt;

    /**
     * 邮件
     */
    @Value("${spring.mail.nickname}")
    private String mallNickname;

    /**
     * 邮件
     */
    @Value("${spring.mail.username}")
    private String mallAccount;

}
