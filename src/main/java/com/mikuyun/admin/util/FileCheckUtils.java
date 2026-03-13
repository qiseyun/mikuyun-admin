package com.mikuyun.admin.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.mikuyun.admin.enums.FileTypeEnum;
import com.mikuyun.admin.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2024/7/15 下午2:04
 */
@Slf4j
public class FileCheckUtils {

    /**
     * 生成文件名并判断是否是对应类型的文件
     *
     * @param originalFilename 原始文件名
     * @return {@link String}
     */
    public static String generateFilePathToType(String originalFilename, FileTypeEnum type) {
        String extension = StringUtils.getFilenameExtension(originalFilename);
        if (!isType(extension, type)) {
            throw new BizException("上传文件不是相关类型的文件");
        }
        return generateCommonFilePath(originalFilename, type.getType());
    }

    /**
     * 生成文件路径
     *
     * @param originalFilename 原始文件名
     * @return {@link String}
     */
    public static String generateCommonFilePath(String originalFilename, String type) {
        String originalFilenameUrlEncode = URLUtil.encode(originalFilename, StandardCharsets.UTF_8);
        return type + "/" + LocalDate.now() + "/" + IdUtil.simpleUUID().substring(0, 8) + "_" + originalFilenameUrlEncode;
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
        String[] suffix = typeEnum.getSuffix();
        return Arrays.asList(suffix).contains("*") || Arrays.asList(suffix).contains(fileName);
    }

}
