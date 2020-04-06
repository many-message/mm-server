package cn.finull.mm.server.service;

import cn.finull.mm.server.vo.resp.RespVO;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 14:33
 */
public interface EmailService {

    /**
     * 向邮箱发送验证码
     * @param email 邮箱
     * @return
     */
    RespVO sendCode(String email);

    /**
     * 异步发送邮件
     * @param to 目标邮件
     * @param subject 主题
     * @param text 正文
     */
    void sendSimpleMail(String to, String subject, String text);
}
