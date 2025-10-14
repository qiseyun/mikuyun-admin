package com.mikuyun.admin;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.mikuyun.admin.util.IdEncodeUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "dev")
@EnableConfigurationProperties
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MikuyunAdminApplicationTests {

    @Test
    public void pwdDecrypt() {

        String pwd = "7Ce71kR0ofkgW7E+5GIiYg==";
        String s = SaSecureUtil.aesDecrypt("qiseyun", pwd);
        System.out.println(s);

    }

    @Test
    public void IdEncodeAndDecrypt() {
        Integer id = 88888;
        String idEncode = IdEncodeUtils.encode(id);
        System.out.println("id." + id + "编码为：" + idEncode);
        Integer idDecode = IdEncodeUtils.decode(idEncode);
        System.out.println(idDecode);

    }

}
