package com.mikuyun.admin.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2022/11/1 14:55
 */
@Configuration
public class MvcConfigure implements WebMvcConfigurer {

    /**
     * 注册拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/demo/**",
                        "/auth/login",
                        "/api/users/login",
                        "/api/users/register",
                        "/api/rights?flag=front",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                );
    }

}
