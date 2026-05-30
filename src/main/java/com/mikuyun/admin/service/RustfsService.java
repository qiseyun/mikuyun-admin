package com.mikuyun.admin.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * RustFS S3-compatible 对象存储服务
 *
 * @author mikuyun
 * @since 2026/5/30
 */
public interface RustfsService {

    /**
     * 上传文件到 RustFS
     *
     * @param inputStream 文件输入流
     * @param objectName  对象名称（包含路径）
     * @param contentType 文件内容类型
     * @return 文件访问 URL
     */
    String upload(InputStream inputStream, String objectName, String contentType);

    /**
     * 上传文件到 RustFS
     *
     * @param file       文件
     * @param objectName 对象名称（包含路径）
     * @return 文件访问 URL
     */
    String upload(MultipartFile file, String objectName);

}
