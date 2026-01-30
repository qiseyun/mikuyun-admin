package com.mikuyun.admin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @auth mikuyun
 * @date 2026/1/30 17:04
 */
@Data
@Component
@ConfigurationProperties(prefix = "qiniu")
public class QiniuProperties {

    private String region;

    private String accessKey;

    private String secretKey;

    private String excelBucket;
    private String excelFileUrl;

    private String imageBucket;
    private String imageFileUrl;

    private String commonFileBucket;
    private String commonFileUrl;

}
