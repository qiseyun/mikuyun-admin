package com.mikuyun.admin.util;


import com.mikuyun.admin.exception.BizException;

import java.util.Base64;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025-5-1 11:00
 */
public class IdEncodeUtils {

    private static final String KEY = "00mi0ku0yun0";

    /**
     * 编码
     *
     * @param id 需要编码的id
     * @return 编码后的16位字符串
     */
    public static String encode(Integer id) {
        // 1. 数字ID左边填充0至12位长度
        String idStr = String.format("%012d", id);
        StringBuilder xorResult = xor(idStr);
        // 3. 对异或结果进行Base64编码
        byte[] xorBytes = xorResult.toString().getBytes();
        String base64Encoded = Base64.getEncoder().encodeToString(xorBytes);
        // 4. 移除字符串后面的=
        return base64Encoded.replaceAll("=", "");
    }

    /**
     * 解码
     *
     * @param encodeId 编码id
     * @return 解码后的id
     */
    public static Integer decode(String encodeId) {
        try {
            // 1. 添加=填充字符
            int mod = encodeId.length() % 4;
            if (mod != 0) {
                int paddingLength = 4 - mod;
                encodeId = encodeId + "=".repeat(paddingLength);
            }
            // 2. Base64解码
            byte[] decodedBytes = Base64.getDecoder().decode(encodeId);
            String base64Result = new String(decodedBytes);
            // 3. 异或解密
            StringBuilder xorResult = xor(base64Result);
            // 4. 去除填充的0
            String originalIdStr = xorResult.toString().replaceAll("^0+", "");
            return Integer.parseInt(originalIdStr);
        } catch (Exception e) {
            throw new BizException("id解码失败");
        }
    }

    /**
     * 异或
     */
    private static StringBuilder xor(String xorResult) {
        StringBuilder decryptedIdStr = new StringBuilder();
        for (int i = 0; i < xorResult.length(); i++) {
            char xorChar = xorResult.charAt(i);
            char keyChar = KEY.charAt(i);
            int decryptedChar = xorChar ^ keyChar;
            decryptedIdStr.append((char) decryptedChar);
        }
        return decryptedIdStr;
    }

}
