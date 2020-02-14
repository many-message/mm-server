package cn.finull.mm.server.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 14:47
 */
@SpringBootTest
class EmailServiceTest {

    @Autowired
    private EmailService emailService;

    /**
     * 测试邮件发送
     */
    @Test
    void sendSimpleMail() {
        emailService.sendCode("chenxi1970@163.com");
    }
}