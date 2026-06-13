package com.mikuyun.admin.interceptor;

import cn.dev33.satoken.interceptor.SaInterceptor;
import com.mikuyun.admin.annotation.TokenIgnore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;

/**
 * @auth mikuyun
 * @since 2026/6/12 20:53
 */
public class MyInterceptor extends SaInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (tokenIgnoreCheck(handler)) {
            return true;
        }
        return super.preHandle(request, response, handler);
    }

    /**
     * 忽略token检查
     *
     * @param handler 选择处理程序执行，用于类型和/或实例评估
     * @return boolean
     */
    private boolean tokenIgnoreCheck(Object handler) {
        // 检查handler是否有跳过注解
        if (handler instanceof HandlerMethod handlerMethod) {
            // 检查方法级别注解
            TokenIgnore tokenIgnoreAnnotation = handlerMethod.getMethodAnnotation(TokenIgnore.class);
            if (tokenIgnoreAnnotation != null) {
                return true;
            }
            // 检查类级别注解
            tokenIgnoreAnnotation = handlerMethod.getBeanType().getAnnotation(TokenIgnore.class);
            return tokenIgnoreAnnotation != null;
        }
        return false;
    }

}
