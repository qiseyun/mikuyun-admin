package com.mikuyun.admin;

import cn.dev33.satoken.secure.SaSecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MikuyunAdminApplicationTests {

    @Test
    void contextLoads() {

        //        String pwd = "dbhFZ6KxKRiXwdeIzBb6vA==";
        String pwd = "7Ce71kR0ofkgW7E+5GIiYg==";
        String s = SaSecureUtil.aesDecrypt("qiseyun", pwd);
        System.out.println(s);

    }

}
