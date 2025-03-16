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
 * @author qiseyun
 * @version 1.0
 * @date 2023年3月25日/0025 0点17分
 * 丝袜哥配置
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MIKUYUN REST-FUL API")
                        .contact(new Contact())
                        .description("MIKUYUN REST-FUL API")
                        .version("v1.0.0")
                        .license(new License().name("").url(""))
                )
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8091")
                                .description("mikuyun-admin")))
                .externalDocs(new ExternalDocumentation()
                        .description("外部文档")
                        .url(""))
                .addSecurityItem(new SecurityRequirement()
                        .addList("apiKey"))
                .components(new Components()
                        .addSecuritySchemes("apiKey", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization"))); // 配置 API 密钥

    }

}
