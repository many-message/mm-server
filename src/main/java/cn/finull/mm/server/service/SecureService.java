package cn.finull.mm.server.service;

/**
 * Description
 * <p> 加解密
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 11:11
 */
public interface SecureService {

    /**
     * rsa公钥加密
     * @param content 未加密的文本
     * @return 加密后的文本
     */
    String rsaPublicKeyEncrypt(String content);

    /**
     * rsa私钥解密
     * @param base64 加密后的base64文本
     * @return 解密后的文本
     */
    String rsaPrivateKeyDecrypt(String base64);

    /**
     * 校验密码强度是否合法
     * @param pwd 密码
     * @return true:合法；false:不合法
     */
    Boolean checkPwd(String pwd);

    /**
     * 使用bcrypt对文本进行hash算法
     * @param content
     * @return
     */
    String hashByBCrypt(String content);

    /**
     * 校验未加密的文本与加密后的文本是否一致
     * @param content 未加密的文本
     * @param hashed 加密后的文本
     * @return true:一致；false:不一致
     */
    Boolean checkByBCrypt(String content, String hashed);
}
