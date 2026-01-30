package com.mikuyun.admin.service.qiniu;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.mikuyun.admin.exception.BizException;
import com.mikuyun.admin.service.IQiniuService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDate;

/**
 * @auth mikuyun
 * @date 2026/1/30 11:28
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class QiniuServiceImpl implements IQiniuService {

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.region}")
    private String region;

    /**
     * 输入流上传
     *
     * @param inputStream 文件输入流
     * @param fileName    文件名称(/yyyy-MM-dd/xxxx.xxx)
     * @return 文件下载链接
     */
    @Override
    public String inputStreamUpload(InputStream inputStream, String fileName) {
        String filePath = LocalDate.now() + "/" + fileName;
        // 构造一个带指定 Region 对象的配置类
        Configuration cfg = Configuration.create(Region.createWithRegionId(region));
        // 指定分片上传版本
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(inputStream, filePath, upToken, null, null);
            // 解析上传成功的结果 DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            if (!response.isOK()) {
                log.error("file upload error: {}", JSON.toJSONString(response));
                throw new BizException(response.toString());
            }
        } catch (QiniuException ex) {
            log.error("file upload error", ex);
        }
        return filePath;
    }

}
