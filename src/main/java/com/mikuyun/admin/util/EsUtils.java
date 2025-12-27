package com.mikuyun.admin.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONUtil;
import com.mikuyun.admin.vo.SearchAfterResult;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jiangQL
 * @version 1.0
 * @date 2025/12/27 22:25
 */
public class EsUtils {

    public static String encodeSearchAfterToBase64(Object[] searchAfter) {
        if (searchAfter == null || searchAfter.length == 0) {
            return null;
        }
        try {
            // 使用 Hutool 的 JSONUtil 将 Object[] 转为 JSON 字符串，再转 byte[]
            byte[] jsonBytes = JSONUtil.toJsonStr(searchAfter).getBytes(java.nio.charset.StandardCharsets.UTF_8);
            // 使用 Hutool Base64 编码（默认是标准 Base64，如需 URL 安全需替换字符）
            String base64Str = Base64.encode(jsonBytes);
            // 转为 URL 安全格式（可选但推荐）
            return base64Str.replace('+', '-').replace('/', '_').replace("=", ""); // 移除填充
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to encode search_after cursor", e);
        }
    }

    public static Object[] decodeSearchAfterFromBase64(String encodedCursor) {
        if (encodedCursor == null || encodedCursor.isEmpty()) {
            return null;
        }
        try {
            // 还原 URL 安全 Base64 为标准 Base64（补回 = 填充）
            String padded = encodedCursor;
            int mod4 = padded.length() % 4;
            // 补等号
            if (mod4 > 0) {
                padded += "====".substring(mod4);
            }
            String standardBase64 = padded.replace('-', '+').replace('_', '/');
            byte[] jsonBytes = Base64.decode(standardBase64);
            String jsonStr = new String(jsonBytes, java.nio.charset.StandardCharsets.UTF_8);
            // 使用 JSONUtil 反序列化为 Object[]
            return JSONUtil.toList(jsonStr, Object.class).toArray();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid search_after cursor: " + encodedCursor, e);
        }
    }

    /**
     *
     * @param size 每页数量
     * @param hits es结果
     * @return SearchAfterResult<T>
     */
    public static <T> SearchAfterResult<T> getDocSearchAfterResult(int size, SearchHits<T> hits) {
        SearchAfterResult<T> result = new SearchAfterResult<>();
        // 提取内容
        List<T> content = hits.getSearchHits().stream()
                .map(SearchHit::getContent)
                .collect(Collectors.toList());
        Object[] nextSearchAfter = null;
        boolean hasNext = false;
        if (!content.isEmpty() && content.size() == size) {
            // 取最后一条的 sort values 作为下一页游标
            SearchHit<T> lastHit = hits.getSearchHits().getLast();
            nextSearchAfter = lastHit.getSortValues().toArray();
            hasNext = true;
            result.setNextSearchAfter(encodeSearchAfterToBase64(nextSearchAfter));
        }
        result.setEsContent(content);
        result.setHasNext(hasNext);
        return result;
    }

}
