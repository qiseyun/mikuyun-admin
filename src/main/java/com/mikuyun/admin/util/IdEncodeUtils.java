package com.mikuyun.admin.util;


import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025-5-1 11:00
 */
public class IdEncodeUtils {

    private static final String KEY = "qwertyuiopasdfghjklz";

    private static final byte[] KEY_BYTES = KEY.getBytes(StandardCharsets.UTF_8);

    private static final Integer KEY_LENGTH = KEY.length();

    /**
     * id编码
     *
     * @param id id
     * @return 编码后字符串
     */
    public static String encode(Long id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("ID must be non-negative");
        }
        // 1. 转为20位字符串
        String padded = String.format("%0" + KEY_LENGTH + "d", id);
        byte[] idBytes = padded.getBytes(StandardCharsets.UTF_8);
        byte[] xored = new byte[KEY_LENGTH];
        for (int i = 0; i < KEY_LENGTH; i++) {
            xored[i] = (byte) (idBytes[i] ^ KEY_BYTES[i]);
        }
        // 3. 使用 URL 和 Filename 安全 类型 base64 编码方案进行编码。
        return Base64.getUrlEncoder().withoutPadding().encodeToString(xored);
    }

    /**
     * 解码
     *
     * @param encoded id编码后字符串
     * @return id
     */
    public static Long decode(String encoded) {
        try {
            // 1. Base64 decode
            byte[] xored = Base64.getUrlDecoder().decode(encoded);
            // 2. 再次解密
            byte[] idBytes = new byte[KEY_LENGTH];
            for (int i = 0; i < KEY_LENGTH; i++) {
                idBytes[i] = (byte) (xored[i] ^ KEY_BYTES[i]);
            }
            // 3. 转换回字符串并解析
            String padded = new String(idBytes, StandardCharsets.UTF_8);
            // 4. 去掉前置零，但保留 "0"
            String replaced = padded.replaceFirst("^0+(?!$)", "");
            return Long.parseLong(replaced);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid encoded ID: " + encoded, e);
        }
    }

    public static void main(String[] args) {
        System.out.println("id编码: " + encode(54315L));
        System.out.println("id解码: " + decode("QUdVQkRJRVlfQFFDVFZXXV5YXU8"));
    }

}
