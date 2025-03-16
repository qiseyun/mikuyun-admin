package com.mikuyun.admin.util;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/1/25 14:28
 */
public class MqSerializationUtils {

    /**
     * 序列化。格式：json字符串、UTF8编码
     */
    public static byte[] serialize(Object object) {
        if (object == null) {
            throw new NullPointerException("object is null");
        }
        return JSON.toJSONString(object).getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 反序列化为Java对象
     *
     * @param bytes 收到的消息body
     * @param clazz 消息对应的类类型
     * @return 反序列化出来的Java对象
     */
    public static <T> T deserialize(byte[] bytes, Class<T> clazz) {
        String jsonStr = new String(bytes, StandardCharsets.UTF_8);
        return JSON.parseObject(jsonStr, clazz);
    }

}
