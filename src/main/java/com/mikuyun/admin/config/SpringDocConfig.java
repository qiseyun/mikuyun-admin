package com.mikuyun.admin.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author mikuyun
 * @since 2025/07/17 20:49
 * <p>
 * SpringDoc OpenAPI 配置类
 * 用于自定义 Swagger UI 和 OpenAPI 文档的元信息（如标题、版本、安全认证方式等）
 */
@Configuration
public class SpringDocConfig {

    /**
     * 定义 OpenAPI 文档的基本信息和安全配置
     * 生成的文档可通过 <a href="http://127.0.0.1:端口/swagger-ui/index.html">openApi地址</a> 访问
     *
     * @return 配置好的 OpenAPI 对象
     */
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                // 设置 API 文档的基本信息（显示在 Swagger UI 顶部）
                .info(new Info()
                        .title("mikuyun-admin REST-FUL API")                     // 文档标题
                        .contact(new Contact())                    // 联系人信息（当前为空）
                        .description("mikuyun-admin REST-FUL API")               // API 的简要描述
                        .version("v1.0.0")                         // API 版本号
                        .license(new License().name("").url(""))   // 开源许可证（当前未设置）
                )
                // 指定 API 服务的访问地址（Swagger UI 中可切换服务器）
                .servers(List.of(
                        new Server()
                                .url("http://127.0.0.1:8091")      // 本地开发地址（注意：生产环境应改为实际域名）
                                .description("本地开发环境")         // 服务器描述（可留空）
                ))
                // 添加外部文档链接（显示在 Swagger UI 底部）
                .externalDocs(new ExternalDocumentation()
                        .description("外部文档")                    // 链接文字
                        .url("")                                   // 外部文档 URL（当前未设置）
                )
                // 全局安全要求：所有接口默认需要携带 apiKey 认证
                .addSecurityItem(new SecurityRequirement()
                        .addList("apiKey")                   // 引用下方定义的 security scheme 名称
                )
                // 定义安全方案（即如何传递认证信息）
                .components(new Components()
                        .addSecuritySchemes("apiKey",                    // 安全方案名称，需与上面 addList 中的名称一致
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)    // 认证类型：API Key（非 OAuth2/JWT 等）
                                        .in(SecurityScheme.In.HEADER)        // API Key 放在请求头中
                                        .name("Authorization")         // 请求头的名称,可自定义，例如 "X-Token" 或 "Authorization" 我这里是Authorization
                        )
                );
    }
}
