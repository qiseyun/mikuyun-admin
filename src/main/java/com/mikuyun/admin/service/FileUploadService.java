package com.mikuyun.admin.service;


import com.mikuyun.admin.common.R;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年4月22日/0022 15点52分
 */
public interface FileUploadService {

    /**
     * 文件记录
     *
     * @param originalName 文件名
     * @param size         文件大小
     * @param type         类型
     * @param url          文件链接
     * @param channel      上传渠道
     * @param hash         唯一hash
     */
    void fileLog(String originalName, Long size, String type, String url, String channel, String hash);

    /**
     * 上传文件 minio
     *
     * @param file 文件
     * @return {@link R}
     */
    String uploadFileMinio(MultipartFile file, String type);

    /**
     * 上传文件 qiniu
     *
     * @param file 文件
     * @return {@link R}
     */
    String uploadFileQiniu(MultipartFile file, String type);

}
