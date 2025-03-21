package com.mikuyun.admin;

import cn.dev33.satoken.SaManager;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@Slf4j
@EnableAsync
@SpringBootApplication
@EnableWebSocket
@MapperScan(value = "com.mikuyun.admin.mapper")
public class MikuyunAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(MikuyunAdminApplication.class, args);
        log.info("启动成功");
        log.info("satoken设置如下：{}", SaManager.getConfig());
    }

}
