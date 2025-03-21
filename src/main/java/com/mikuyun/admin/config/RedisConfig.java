package com.mikuyun.admin.config;

import cn.hutool.core.util.StrUtil;
import com.mikuyun.admin.properties.RedisConfigurationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2024/11/9 23:23
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisConfigurationProperties rcp;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        RedissonClient redissonClient = null;
        try {
            String redisUrl = "redis://%s:%s";
            Config config = new Config();

            SingleServerConfig singleConfig = config.useSingleServer();
            singleConfig.setAddress(String.format(redisUrl, rcp.getHost(), rcp.getPort()))
                    .setDatabase(rcp.getDatabase())
                    .setConnectionPoolSize(rcp.getLettuce().getPool().getMaxActive())
                    .setConnectionMinimumIdleSize(rcp.getLettuce().getPool().getMinIdle());
            if (StrUtil.isNotBlank(rcp.getPassword())) {
                singleConfig.setPassword(rcp.getPassword());
            }
            redissonClient = Redisson.create(config);
            log.info("RedissonClient init success host={} port={}", rcp.getHost(), rcp.getPort());
        } catch (Exception e) {
            log.error("RedissonClient init error host={} port={}", rcp.getHost(), rcp.getPort(), e);
        }
        return redissonClient;
    }

}
