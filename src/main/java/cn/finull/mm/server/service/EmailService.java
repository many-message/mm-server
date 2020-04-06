package cn.finull.mm.server.service;

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
     * 异步调用
     * 向邮箱发送验证码
     * @param email 邮箱
     * @return
     */
    void sendCode(String email);
}
