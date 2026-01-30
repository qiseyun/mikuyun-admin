package com.mikuyun.admin.service.impl;


import cn.hutool.core.util.IdUtil;
import com.mikuyun.admin.common.Constant;
import com.mikuyun.admin.entity.SysFile;
import com.mikuyun.admin.enums.FileTypeEnum;
import com.mikuyun.admin.properties.QiniuProperties;
import com.mikuyun.admin.service.FileUploadService;
import com.mikuyun.admin.service.IQiniuService;
import com.mikuyun.admin.service.ISysFileService;
import com.mikuyun.admin.service.minio.MinioService;
import com.qiniu.storage.model.DefaultPutRet;
import io.minio.ObjectWriteResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

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
        String fileName = fileTypeEnum.getType() + "/" + LocalDate.now() + "/" + file.getOriginalFilename() + "_" + IdUtil.simpleUUID().substring(0, 8);
        // minio 上传文件
        ObjectWriteResponse objectWriteResponse = minioService.uploadFile(file, fileName, file.getContentType());
        // 获取上传文件url
        String fileUrl = minioService.getPublicObjectUrl(objectWriteResponse.object());
        // 文件信息入库
        try {
            fileLog(file, fileTypeEnum.getType(), fileUrl, "minio", getMD5(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fileUrl;
    }

    @Override
    public String uploadFileQiniu(MultipartFile file, String type) {
        FileTypeEnum fileTypeEnum = FileTypeEnum.getEnumByType(type);
        DefaultPutRet defRes;
        String key = type + "/" + LocalDate.now() + "/" + file.getOriginalFilename() + "_" + IdUtil.simpleUUID().substring(0, 8);
        try {
            defRes = qiniuService.inputStreamUpload(file.getInputStream(), key, qiniuProperties.getCommonFileBucket());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String url = qiniuProperties.getCommonFileUrl() + defRes.key;
        // 文件信息入库
        fileLog(file, fileTypeEnum.getType(), url, "qiniu", defRes.hash);
        return url;
    }

    /**
     * 文件查重
     *
     * @param fileHash 文件md5
     * @return QiseyunFile
     */
    public SysFile checkFileRepeat(String fileHash) {
        return ISysFileService
                .lambdaQuery()
                .eq(SysFile::getMd5, fileHash)
                .eq(SysFile::getIsDelete, Constant.STATUS_NORMAL_INT)
                .one();
    }

    /**
     * 获取文件MD5
     *
     * @param fileByte 字节组
     * @return MD5
     */
    public String getMD5(byte[] fileByte) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(fileByte);
            return new BigInteger(1, digest).toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 文件日志 文件管理数据记录,收集管理追踪文件
     *
     * @param file 上传文件
     * @param type 文件类型
     * @param url  文件链接
     */
    private void fileLog(MultipartFile file, String type, String url, String channel, String hash) {
        SysFile sysFile = new SysFile();
        sysFile.setOriginalName(file.getOriginalFilename());
        sysFile.setFileExt(file.getContentType());
        sysFile.setType(type);
        sysFile.setMd5(hash);
        sysFile.setFileSizeByte(String.valueOf(file.getSize()));
        sysFile.setUrl(url);
        sysFile.setChannel(channel);
        ISysFileService.save(sysFile);
    }

}
