package com.mikuyun.admin.service.rustfs;

import com.mikuyun.admin.properties.RustfsProperties;
import com.mikuyun.admin.util.FileCheckUtils;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * RustFS S3-compatible 对象存储服务实现
 *
 * @author mikuyun
 * @since 2026/5/30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RustfsServiceImpl implements RustfsService {

    private final RustfsProperties rustfsProperties;

    /**
     * 获取 MinIO 客户端（S3-compatible，连接 RustFS）
     */
    private MinioClient getClient() {
        return MinioClient.builder()
                .endpoint(rustfsProperties.getEndpoint())
                .credentials(rustfsProperties.getAccessKey(), rustfsProperties.getSecretKey())
                .build();
    }

    /**
     * 确保存储桶存在，不存在则创建
     */
    private void ensureBucket(MinioClient client) {
        try {
            String bucketName = rustfsProperties.getBucketName();
            boolean exists = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                log.info("RustFS bucket created: {}", bucketName);
            }
        } catch (Exception e) {
            log.error("RustFS bucket check/create error", e);
            throw new RuntimeException("RustFS bucket 操作失败", e);
        }
    }

    @Override
    public String upload(InputStream inputStream, String objectName, String contentType) {
        try {
            MinioClient client = getClient();
            ensureBucket(client);
            client.putObject(PutObjectArgs.builder()
                    .bucket(rustfsProperties.getBucketName())
                    .object(objectName)
                    .stream(inputStream, inputStream.available(), -1)
                    .contentType(contentType)
                    .build());
            // 构造文件访问 URL: endpoint/bucketName/objectName
            String url = rustfsProperties.getEndpoint() + "/" + rustfsProperties.getBucketName() + "/" + objectName;
            log.info("RustFS upload success: {}", url);
            return url;
        } catch (Exception e) {
            log.error("RustFS upload error", e);
            throw new RuntimeException("RustFS 文件上传失败", e);
        }
    }

    @Override
    public String upload(MultipartFile file, String objectName) {
        try {
            return upload(file.getInputStream(), objectName, FileCheckUtils.resolveContentType(file));
        } catch (Exception e) {
            log.error("RustFS upload error for file: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("RustFS 文件上传失败", e);
        }
    }

}
