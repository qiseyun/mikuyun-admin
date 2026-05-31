package com.mikuyun.admin.service.qiniu;

import com.alibaba.fastjson2.JSON;
import com.google.gson.Gson;
import com.mikuyun.admin.exception.BizException;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author mikuyun
 * @since 2026/1/30 11:28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QiniuServiceImpl implements IQiniuService {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.region}")
    private String region;

    @Override
    public DefaultPutRet upload(InputStream inputStream, String objectName, String bucket) {
        // 构造一个带指定 Region 对象的配置类
        Configuration cfg = Configuration.create(Region.createWithRegionId(region));
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(inputStream, objectName, upToken, null, null);
            log.info("返回信息: {}", response.bodyString());
            // 解析上传成功的结果
            if (!response.isOK()) {
                log.error("file upload error: {}", JSON.toJSONString(response));
                throw new BizException(response.toString());
            }
            return new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            log.error("file upload error", ex);
            throw new BizException("文件上传失败");
        }
    }

    @Override
    public DefaultPutRet upload(MultipartFile file, String objectName, String bucket) {
        try {
            return upload(file.getInputStream(), objectName, bucket);
        } catch (Exception e) {
            log.error("RustFS upload error for file: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("RustFS 文件上传失败", e);
        }
    }

    @Override
    public String getDownloadUrl(String url, Long seconds) {
        Auth auth = Auth.create(accessKey, secretKey);
        // 1小时，可以自定义链接过期时间
        return auth.privateDownloadUrl(url, seconds != null ? seconds : 300);
    }

}
