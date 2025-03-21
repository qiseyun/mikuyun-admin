package com.mikuyun.admin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2024/11/10 11:05
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.data.redis")
public class RedisConfigurationProperties {

    private String host;

    private String port;

    private String password;

    private int database;

    private Lettuce lettuce;

    @Data
    public static class Lettuce {

        private Pool pool;

        @Data
        public static class Pool {

            private int maxActive;

            private int minIdle;

        }

    }

}
