package com.mikuyun.admin.service;

import com.qiniu.storage.model.DefaultPutRet;

import java.io.InputStream;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2026/1/30 12:44
 */
public interface IQiniuService {

    /**
     * 输入流上传
     *
     * @param inputStream 文件输入流
     * @param key         加路径的文件名称(/yyyy-MM-dd/xxxx.xxx)
     * @param bucket      桶
     * @return 文件源站链接
     */
    DefaultPutRet inputStreamUpload(InputStream inputStream, String key, String bucket);

    /**
     * 获取期限下载链接
     *
     * @param url     原始链接
     * @param seconds 过期时间
     * @return 有期限的下载链接
     */
    String getDownloadUrl(String url, Long seconds);

}
