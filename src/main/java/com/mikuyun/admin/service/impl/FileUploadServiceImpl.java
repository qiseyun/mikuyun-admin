package com.mikuyun.admin.service.impl;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.mikuyun.admin.entity.SysFile;
import com.mikuyun.admin.enums.FileTypeEnum;
import com.mikuyun.admin.properties.QiniuProperties;
import com.mikuyun.admin.service.FileUploadService;
import com.mikuyun.admin.service.IQiniuService;
import com.mikuyun.admin.service.ISysFileService;
import com.mikuyun.admin.service.minio.MinioService;
import com.mikuyun.admin.util.FileCheckUtils;
import com.qiniu.storage.model.DefaultPutRet;
import io.minio.ObjectWriteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年4月22日/0022 15点52分
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final ISysFileService ISysFileService;

    private final IQiniuService qiniuService;

    private final QiniuProperties qiniuProperties;

    private final MinioService minioService;

    @Override
    public String uploadFileMinio(MultipartFile file, String type) {
        FileTypeEnum fileTypeEnum = FileTypeEnum.getEnumByType(type);
        // 文件名
        String fileName = FileCheckUtils.generateFilePathToType(file.getOriginalFilename(), fileTypeEnum);
        // minio 上传文件
        ObjectWriteResponse objectWriteResponse = minioService.uploadFile(file, fileName, file.getContentType());
        // 获取上传文件url
        String fileUrl = minioService.getPublicObjectUrl(objectWriteResponse.object());
        // 文件信息入库
        try {
            fileLog(file.getOriginalFilename(), file.getSize(), fileTypeEnum.getType(), fileUrl, "minio", DigestUtil.sha256Hex(file.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileUrl;
    }

    @Override
    public String uploadFileQiniu(MultipartFile file, String type) {
        FileTypeEnum fileTypeEnum = FileTypeEnum.getEnumByType(type);
        String key = FileCheckUtils.generateFilePathToType(file.getOriginalFilename(), fileTypeEnum);
        DefaultPutRet defRes;
        try {
            defRes = qiniuService.inputStreamUpload(file.getInputStream(), key, qiniuProperties.getCommonFileBucket());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String url = qiniuProperties.getCommonFileUrl() + defRes.key;
        // 文件信息入库
        fileLog(file.getOriginalFilename(), file.getSize(), fileTypeEnum.getType(), url, "qiniu", defRes.hash);
        return url;
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
        if (ObjectUtil.isEmpty(checkFileRepeat(hash))) {
            ISysFileService.save(sysFile);
        }
    }

    /**
     * 文件查重
     *
     * @param fileHash 文件md5
     * @return QiseyunFile
     */
    private SysFile checkFileRepeat(String fileHash) {
        return ISysFileService.lambdaQuery().eq(SysFile::getMd5, fileHash).one();
    }

}
