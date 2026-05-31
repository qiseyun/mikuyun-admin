package com.mikuyun.admin.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @author mikuyun
 * @since 2023年3月25日/0025 0点17分
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    /**
     * 图片
     */
    IMAGE("image", "图片类文件", new String[]{"png", "jpg", "jpeg", "bmp", "svg", "ico", "icon", "webp"}, "IMAGE"),

    /**
     * 安装包
     */
    INSTALL_PACKAGE("package", "安装包类文件", new String[]{"exe", "dmg", "deb", "rpm", "apk", "ipa"}, "COMMON"),

    /**
     * 视频类
     */
    VIDEO("video", "视频类文件", new String[]{"mp4", "gif", "avi", "mov", "wmv", "flv"}, "COMMON"),

    /**
     * 文档报表类
     */
    EXCEL("excel", "文档类文件", new String[]{"xls", "xlsx", "csv"}, "EXCEL"),

    /**
     * 文档报表类
     */
    DOC("document", "文档类文件", new String[]{"txt", "doc", "docx", "ppt", "pdf"}, "EXCEL"),

    /**
     * 默认
     */
    DEFAULT("default", "默认", new String[]{"*"}, "COMMON"),

    ;

    private final String type;

    private final String desc;

    private final String[] suffix;

    private final String bucketKey;

    /**
     * 根据文件后缀名自动判断文件类型
     *
     * @param suffix 文件后缀（不含点），如 "png"、"mp4"
     * @return 匹配的 FileTypeEnum，未匹配时返回 DEFAULT
     */
    public static FileTypeEnum getEnumBySuffix(String suffix) {
        if (suffix == null) {
            return DEFAULT;
        }
        for (FileTypeEnum value : values()) {
            if (value == DEFAULT) {
                continue;
            }
            if (Arrays.asList(value.getSuffix()).contains(suffix)) {
                return value;
            }
        }
        return DEFAULT;
    }

}
