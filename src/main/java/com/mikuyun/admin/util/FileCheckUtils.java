package com.mikuyun.admin.util;

import cn.hutool.core.util.StrUtil;
import com.mikuyun.admin.enums.FileTypeEnum;
import com.mikuyun.admin.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2024/7/15 下午2:04
 */
@Component
@Slf4j
@AllArgsConstructor
public class FileCheckUtils {

    /**
     * 生成文件路径
     *
     * @param originalFilename 原始文件名
     * @return {@link String}
     */
    public static String generateFilePath(String originalFilename) {
        String localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String extension = StringUtils.getFilenameExtension(originalFilename);
        String fileName = UUID.randomUUID().toString();
        return "/" + localDate + "/" + fileName + "." + extension;
    }

    /**
     * 生成文件名并判断是否是对应类型的文件
     *
     * @param originalFilename 原始文件名
     * @return {@link String}
     */
    public static String generateFilePathToType(String originalFilename, FileTypeEnum type) {
        String localDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String extension = StringUtils.getFilenameExtension(originalFilename);
        if (!isType(extension, type)) {
            throw new BizException("上传文件不是相关类型的文件");
        }
        String fileName = UUID.randomUUID().toString();
        return "/" + localDate + "/" + fileName + "." + extension;
    }

    /**
     * 判断文件后缀是否为符合条件
     *
     * @param fileName 后缀
     * @param typeEnum 文件类型枚举
     * @return boolean
     */
    public static Boolean isType(String fileName, FileTypeEnum typeEnum) {
        if (StrUtil.isBlank(fileName)) {
            return false;
        }
        String[] arr = typeEnum.getSuffix();
        return Arrays.asList(arr).contains(fileName);
    }

}
