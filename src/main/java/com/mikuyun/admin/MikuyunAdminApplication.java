package com.mikuyun.admin;

import cn.dev33.satoken.SaManager;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Slf4j
@EnableAsync
@SpringBootApplication
@EnableWebSocket
@EnableScheduling
@MapperScan(value = "com.mikuyun.admin.mapper")
public class MikuyunAdminApplication {

    @Value("${spring.env}")
    private String env;

    @Value("${server.port}")
    private String port;

    @Value("${sa-token.token-name}")
    private String tokenName;

    @Value("${xxl.job.enabled}")
    private String xxlJobEnabled;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MikuyunAdminApplication.class, args);
        MikuyunAdminApplication app = context.getBean(MikuyunAdminApplication.class);
        log.info("MikuyunAdminApplication started successfully ----> env: {}, port: {}, tokenName: {}, xxl-job: {}", app.env, app.port, app.tokenName, app.xxlJobEnabled);
    }

}
