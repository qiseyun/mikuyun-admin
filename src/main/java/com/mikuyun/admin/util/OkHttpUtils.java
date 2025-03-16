package com.mikuyun.admin.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mikuyun.admin.common.R;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author qise
 * @date 2025/3/16 10:39
 */
@Slf4j
public class OkHttpUtils {

    public static <T> R<T> get(String url, Class<T> cls) {
        return get(url, null, null, null, cls, true);
    }

    public static <T> R<T> get(String url, Long timeout, Class<T> cls) {
        return get(url, null, null, timeout, cls, true);
    }

    public static <T> R<T> get(String url, Class<T> cls, boolean outLog) {
        return get(url, null, null, null, cls, outLog);
    }

    public static <T> R<T> get(String url, Long timeout, Class<T> cls, boolean outLog) {
        return get(url, null, null, timeout, cls, outLog);
    }

    public static <T> R<T> postJson(String url, JSONObject params, Map<String, String> headers, Class<T> cls) {
        return postJson(url, params, headers, null, cls, true);
    }

    public static <T> R<T> postJson(String url, JSONObject params, Map<String, String> headers, Class<T> cls, boolean outLog) {
        return postJson(url, params, headers, null, cls, outLog);
    }

    public static <T> R<T> postJson(String url, JSONObject params, Class<T> cls) {
        return postJson(url, params, null, null, cls, true);
    }

    public static <T> R<T> postJson(String url, JSONObject params, Class<T> cls, boolean outLog) {
        return postJson(url, params, null, null, cls, outLog);
    }

    public static <T> R<T> postJson(String url, JSONObject params, Long timeout, Class<T> cls) {
        return postJson(url, params, null, timeout, cls, true);
    }

    public static <T> R<T> postJson(String url, JSONObject params, Long timeout, Class<T> cls, boolean outLog) {
        return postJson(url, params, null, timeout, cls, outLog);
    }

    public static <T> R<T> postForm(String url, Map<String, String> params, Class<T> cls) {
        return postForm(url, params, null, null, cls, true);
    }

    public static <T> R<T> postForm(String url, Map<String, String> params, Long timeout, Class<T> cls) {
        return postForm(url, params, null, timeout, cls, true);
    }

    public static <T> R<T> postForm(String url, Map<String, String> params, Class<T> cls, boolean outLog) {
        return postForm(url, params, null, null, cls, outLog);
    }

    public static <T> R<T> postForm(String url, Map<String, String> params, Long timeout, Class<T> cls, boolean outLog) {
        return postForm(url, params, null, timeout, cls, outLog);
    }

    /**
     * 发起get请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @param timeout 请求超时时间 单位毫秒
     * @param cls     返回结果类型
     * @param outLog  是否打印请求日志
     * @param <T>     自定义返回实体类
     * @return R<T>
     */
    public static <T> R<T> get(String url, String params, Map<String, String> headers, Long timeout, Class<T> cls, boolean outLog) {
        url = StrUtil.isBlank(params) ? url : StrUtil.join("?", url, params);
        Request request = new Request.Builder().url(url).get().build();
        R<T> result = execute(request, timeout, headers, cls);
        if (outLog) {
            log.info("okHttp-get url={} params={} result={}", url, params, JSON.toJSONString(result));
        }
        return result;
    }

    /**
     * 发起postJson请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @param timeout 请求超时时间 单位毫秒
     * @param cls     返回结果类型
     * @param outLog  是否打印请求日志
     * @param <T>     自定义返回实体类
     * @return R<T>
     */
    public static <T> R<T> postJson(String url, JSONObject params, Map<String, String> headers, Long timeout, Class<T> cls, boolean outLog) {
        Request.Builder builder = new Request.Builder().url(url);
        builder.addHeader("Content-Type", "application/json");
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), params.toJSONString());
        builder.method("POST", requestBody);
        Request request = builder.build();
        R<T> result = execute(request, timeout, headers, cls);
        if (outLog) {
            log.info("okHttp-postJson url={} params={} result={}", url, params.toJSONString(), JSON.toJSONString(result));
        }
        return result;
    }

    /**
     * 发起postForm请求
     *
     * @param url     请求地址
     * @param params  请求参数
     * @param headers 请求头
     * @param timeout 请求超时时间 单位毫秒
     * @param cls     返回结果类型
     * @param outLog  是否打印请求日志
     * @param <T>     自定义返回实体类
     * @return R<T>
     */
    public static <T> R<T> postForm(String url, Map<String, String> params, Map<String, String> headers, Long timeout, Class<T> cls, boolean outLog) {
        Request.Builder builder = new Request.Builder().url(url);
        builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
        FormBody.Builder formBuilder = new FormBody.Builder();
        if (CollectionUtil.isNotEmpty(params)) {
            params.forEach(formBuilder::add);
        }
        builder.post(formBuilder.build());
        Request request = builder.build();
        R<T> result = execute(request, timeout, headers, cls);
        if (outLog) {
            log.info("okHttp-postForm url={} params={} result={}", url, JSON.toJSONString(params), JSON.toJSONString(result));
        }
        return result;
    }

    /**
     * 执行请求
     *
     * @param request 请求实体
     * @param timeout 超时时间
     * @param headers 请求头
     * @param cls     返回结果构建的实体类
     * @param <T>     自定义返回实体类
     * @return R<T>
     */
    public static <T> R<T> execute(Request request, Long timeout, Map<String, String> headers, Class<T> cls) {
        OkHttpClient client = getClient(timeout);
        Map<String, String> initHeaders = initHeaders();
        if (CollectionUtil.isNotEmpty(headers)) {
            initHeaders.putAll(headers);
        }
        Request.Builder builder = request.newBuilder();
        initHeaders.forEach(builder::addHeader);
        request = builder.build();
        String result;
        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            result = response.body().string();
            T data = JSON.parseObject(result, cls);
            return R.ok(data);
        } catch (IOException e) {
            log.error("okHttp-IoException-error errorMsg={} 错误堆栈", e.getMessage(), e);
            return R.failed();
        } catch (Exception e) {
            log.error("okHttp-exception-error errorMsg={} 错误堆栈", e.getMessage(), e);
            return R.failed();
        }
    }

    private static final Map<Long, OkHttpClient> OK_HTTP_CLIENT_MAP = new HashMap<>();

    /**
     * 优化okHttp实例,全局唯一,避免在多线程情况下无限创建请求线程
     *
     * @param timeout 超时时间
     * @return OkHttpClient
     */
    private synchronized static OkHttpClient getClient(Long timeout) {
        if (!OK_HTTP_CLIENT_MAP.containsKey(timeout)) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (timeout != null && timeout > 0) {
                builder.connectTimeout(timeout, TimeUnit.SECONDS)
                        .readTimeout(timeout, TimeUnit.SECONDS)
                        .writeTimeout(timeout, TimeUnit.SECONDS)
                        .build();
            }
            OkHttpClient httpClient = builder.build();
            OK_HTTP_CLIENT_MAP.put(timeout, httpClient);
        }
        return OK_HTTP_CLIENT_MAP.get(timeout);
    }

    /**
     * 放入常规请求头
     *
     * @return Map<String, String>
     */
    private static Map<String, String> initHeaders() {
        Map<String, String> headers = new HashMap<>(8);
        headers.put("accept", "*/*");
        headers.put("connection", "Keep-Alive");
        headers.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        return headers;
    }

}
