package com.mikuyun.admin.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.mikuyun.admin.enums.FileTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * @author mikuyun
 * @since 2024/7/15 下午2:04
 */
@Slf4j
public class FileCheckUtils {

    /**
     * 根据文件名自动检测类型并生成文件路径，格式: /年/月/日/类型/
     *
     * @param originalFilename 原始文件名
     * @return {@link String}
     */
    public static String generateFilePath(String originalFilename) {
        String extension = StringUtils.getFilenameExtension(originalFilename);
        FileTypeEnum typeEnum = FileTypeEnum.getEnumBySuffix(extension);
        return generateCommonFilePath(originalFilename, typeEnum.getType());
    }

    /**
     * 生成文件路径，命名规则: 年/月/日/类型/毫秒时间戳_短uuid.扩展名
     *
     * @param originalFilename 原始文件名（用于提取扩展名）
     * @param type             文件类型
     * @return {@link String}
     */
    public static String generateCommonFilePath(String originalFilename, String type) {
        String extension = StringUtils.getFilenameExtension(originalFilename);
        LocalDate now = LocalDate.now();
        return now.getYear() + "/" + now.getMonthValue() + "/" + now.getDayOfMonth()
                + "/" + type + "/" + System.currentTimeMillis() + "_" + IdUtil.simpleUUID().substring(0, 8)
                + (extension != null ? "." + extension : "");
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
