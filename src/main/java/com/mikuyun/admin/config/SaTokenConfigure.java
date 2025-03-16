package com.mikuyun.admin.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author qiseyun
 * @version 1.0
 * @date 2022/11/1 14:55
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {

    /**
     * 注册拦截器
     *
     * @param registry 拦截器注册表
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，校验规则为 StpUtil.checkLogin() 登录校验。
        SaInterceptor saInterceptor = new SaInterceptor(handler -> StpUtil.checkLogin());
        // 关闭他的自动识别注解
        saInterceptor.isAnnotation(false);
        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(saInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/auth/login",
                        "/api/users/login",
                        "/api/users/register",
                        "/api/rights?flag=front",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                );
    }

}
