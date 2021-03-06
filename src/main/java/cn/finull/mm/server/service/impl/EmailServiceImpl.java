package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.service.EmailService;
import cn.finull.mm.server.util.CacheUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Description
 * <p> 邮件发送
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 14:33
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    @Override
    public void sendCode(String email) {
        String code = RandomUtil.randomNumbers(6);
        CacheUtil.putEmailAndCode(email, code);

        String subject = "[MM] Please verify your email";
        String text = String.format("Your verification code is: %s", code);

        sendSimpleMail(email, subject, text);
    }

    public void sendSimpleMail(String to, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(String.format("mm<%s>", from));
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        log.info("开始向{}发送邮件！", to);
        javaMailSender.send(mailMessage);
        log.info("成功向{}发送邮件！", to);
    }
}
