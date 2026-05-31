package com.mikuyun.admin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * RustFS S3-compatible 对象存储配置
 *
 * @author mikuyun
 * @since 2026/5/30
 */
@Data
@Component
@ConfigurationProperties(prefix = "rustfs")
public class RustfsProperties {

    /**
     * RustFS 服务端点
     */
    private String endpoint;

    /**
     * 访问密钥
     */
    private String accessKey;

    /**
     * 秘密密钥
     */
    private String secretKey;

    /**
     * 存储桶名称
     */
    private String bucketName;

    /**
     * 批量上传最大数量，默认10
     */
    private Integer batchLimit = 10;

}
