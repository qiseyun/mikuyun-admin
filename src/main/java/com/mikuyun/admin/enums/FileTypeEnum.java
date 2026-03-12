package com.mikuyun.admin.enums;

import com.mikuyun.admin.exception.BizException;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2023年3月25日/0025 0点17分
 */
@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    /**
     * 图片
     */
    IMAGE("image", "图片类文件", new String[]{"png", "jpg", "jpeg", "bmp", "svg", "ico", "icon"}),

    /**
     * 安装包
     */
    INSTALL_PACKAGE("package", "安装包类文件", new String[]{"exe", "dmg", "deb", "rpm", "apk", "ipa"}),

    /**
     * 视频类
     */
    VIDEO("video", "视频类文件", new String[]{"mp4", "gif", "avi", "mov", "wmv", "flv"}),

    /**
     * 视频类
     */
    DOC("document", "文档类文件", new String[]{"txt", "doc", "docx", "ppt", "xls", "xlsx", "pdf"}),

    /**
     * 测试文件
     */
    TEST("test", "测试", new String[]{"*"}),

    ;

    private final String type;

    private final String desc;

    private final String[] suffix;

    public static FileTypeEnum getEnumByType(String type) {
        if (type == null) {
            throw new BizException("文件类型错误");
        }
        for (FileTypeEnum value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        throw new BizException("不支持的文件类型");
    }

}
