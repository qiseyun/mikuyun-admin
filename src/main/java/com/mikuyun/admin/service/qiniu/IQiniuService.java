package com.mikuyun.admin.service.qiniu;

import com.qiniu.storage.model.DefaultPutRet;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author mikuyun
 * @since 2026/1/30 12:44
 */
public interface IQiniuService {

    /**
     * 上传文件到 qiniu
     *
     * @param inputStream 文件输入流
     * @param objectName  对象名称（包含路径）
     * @param bucket      桶
     * @return 文件访问 URL
     */
    DefaultPutRet upload(InputStream inputStream, String objectName, String bucket);

    /**
     * 上传文件到 qiniu
     *
     * @param file       文件
     * @param objectName 对象名称（包含路径）
     * @param bucket     桶
     * @return 文件访问 URL
     */
    DefaultPutRet upload(MultipartFile file, String objectName, String bucket);

    /**
     * 获取期限下载链接
     *
     * @param url     原始链接
     * @param seconds 过期时间
     * @return 有期限的下载链接
     */
    String getDownloadUrl(String url, Long seconds);

}
