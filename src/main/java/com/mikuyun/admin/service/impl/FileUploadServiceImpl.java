package com.mikuyun.admin.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.mikuyun.admin.common.Constant;
import com.mikuyun.admin.entity.SysFile;
import com.mikuyun.admin.enums.FileTypeEnum;
import com.mikuyun.admin.service.FileUploadService;
import com.mikuyun.admin.service.QiseFileService;
import com.mikuyun.admin.service.minio.MinioService;
import com.mikuyun.admin.util.FileCheckUtils;
import io.minio.ObjectWriteResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年4月22日/0022 15点52分
 */
@Slf4j
@Service
@AllArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final QiseFileService qiseFileService;

    private final MinioService minioService;

    @Override
    public String uploadFile(MultipartFile file, String type) {
        // 重复文件校验
        try {
            SysFile sysFile = checkFileRepeat(getMD5(file.getBytes()));
            if (ObjectUtil.isNotEmpty(sysFile)) {
                return sysFile.getUrl();
            }
        } catch (IOException e) {
            log.error("getMD5 error message: {}", e.getMessage());
        }
        FileTypeEnum fileTypeEnum = FileTypeEnum.getEnumByType(type);
        // 文件名
        String fileName = fileTypeEnum.getType() + FileCheckUtils.generateFilePathToType(file.getOriginalFilename(), fileTypeEnum);
        // minio 上传文件
        ObjectWriteResponse objectWriteResponse = minioService.uploadFile(file, fileName, file.getContentType());
        // 获取上传文件url
        String fileUrl = minioService.getPublicObjectUrl(objectWriteResponse.object());
        Map<String, String> map = new HashMap<>();
        map.put("fileName", fileName);
        map.put("type", fileTypeEnum.getType());
        map.put("url", fileUrl);
        // 文件信息入库
        fileLog(file, map);
        return fileUrl;
    }

    /**
     * 文件查重
     *
     * @param fileHash 文件md5
     * @return QiseyunFile
     */
    public SysFile checkFileRepeat(String fileHash) {
        return qiseFileService
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
     * @param file     上传文件
     * @param fileInfo 文件信息
     */
    private void fileLog(MultipartFile file, Map<String, String> fileInfo) {
        SysFile sysFile = new SysFile();
        sysFile.setOriginalName(fileInfo.get("fileName"));
        sysFile.setType(fileInfo.get("type"));
        sysFile.setFileSizeByte(String.valueOf(file.getSize()));
        sysFile.setUrl(fileInfo.get("url"));
        qiseFileService.save(sysFile);
    }

}
