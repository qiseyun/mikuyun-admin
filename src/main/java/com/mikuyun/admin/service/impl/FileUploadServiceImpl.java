package com.mikuyun.admin.service.impl;


import cn.hutool.crypto.digest.DigestUtil;
import com.mikuyun.admin.entity.SysFile;
import com.mikuyun.admin.enums.FileTypeEnum;
import com.mikuyun.admin.properties.QiniuProperties;
import com.mikuyun.admin.service.FileUploadService;
import com.mikuyun.admin.service.qiniu.IQiniuService;
import com.mikuyun.admin.service.ISysFileService;
import com.mikuyun.admin.service.rustfs.RustfsService;
import com.mikuyun.admin.util.FileCheckUtils;
import com.qiniu.storage.model.DefaultPutRet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author mikuyun
 * @since 2023年4月22日/0022 15点52分
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final ISysFileService ISysFileService;

    private final IQiniuService qiniuService;

    private final QiniuProperties qiniuProperties;

    private final RustfsService rustfsService;

    @Override
    public String uploadFileQiniu(MultipartFile file) {
        try {
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            FileTypeEnum fileTypeEnum = FileTypeEnum.getEnumBySuffix(extension);
            String key = FileCheckUtils.generateFilePath(file.getOriginalFilename());
            DefaultPutRet defRes = qiniuService.upload(file.getInputStream(), key, getBucket(fileTypeEnum));
            String url = qiniuProperties.getCommonFileUrl() + defRes.key;
            // 文件信息入库
            fileLog(file.getOriginalFilename(), file.getSize(), fileTypeEnum.getType(), url, "qiniu", DigestUtil.sha256Hex(file.getInputStream()));
            return url;
        } catch (IOException e) {
            log.error("qiniu file upload error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public String uploadFileRustfs(MultipartFile file) {
        try {
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            FileTypeEnum fileTypeEnum = FileTypeEnum.getEnumBySuffix(extension);
            String key = FileCheckUtils.generateFilePath(file.getOriginalFilename());
            String url = rustfsService.upload(file, key);
            // 文件信息入库
            fileLog(file.getOriginalFilename(), file.getSize(), fileTypeEnum.getType(), url, "rustfs", DigestUtil.sha256Hex(file.getInputStream()));
            return url;
        } catch (IOException e) {
            log.error("rustfs file upload error");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fileLog(String originalName, Long size, String type, String url, String channel, String hash) {
        SysFile sysFile = new SysFile();
        sysFile.setOriginalName(originalName);
        sysFile.setFileExt(StringUtils.getFilenameExtension(originalName));
        sysFile.setType(type);
        sysFile.setMd5(hash);
        sysFile.setFileSizeByte(String.valueOf(size));
        sysFile.setUrl(url);
        sysFile.setChannel(channel);
        ISysFileService.save(sysFile);
    }

    /**
     * 根据文件类型枚举获取真实的 bucket 名称
     */
    private String getBucket(FileTypeEnum fileType) {
        return switch (fileType.getBucketKey()) {
            case "IMAGE" -> qiniuProperties.getImageBucket();
            case "EXCEL" -> qiniuProperties.getExcelBucket();
            default -> qiniuProperties.getCommonFileBucket();
        };
    }

    /**
     * 根据后缀一站式获取 bucket
     */
    private String getBucketBySuffix(String suffix) {
        FileTypeEnum fileType = FileTypeEnum.getEnumBySuffix(suffix);
        return getBucket(fileType);
    }

}
