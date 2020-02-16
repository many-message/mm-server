package cn.finull.mm.server.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 11:48
 */
@Slf4j
@SpringBootTest
class SecureServiceTest {

    @Autowired
    private SecureService secureService;

    /**
     * 测试 rsa公钥加密、私钥解密
     */
    @Test
    public void testRsa() {
        String pwd = "123456";

        String encryptStr = secureService.rsaPublicKeyEncrypt(pwd);
        String decryptStr = secureService.rsaPrivateKeyDecrypt(encryptStr);

        assertEquals(pwd, decryptStr);
    }

    @Test
    public void testBCrypt() {
        String pwd = "admin123456";

        String hashed = secureService.hashByBCrypt(pwd);
        log.info("pwd:[{}]", hashed);
        assertTrue(secureService.checkByBCrypt(pwd, hashed));
    }
}