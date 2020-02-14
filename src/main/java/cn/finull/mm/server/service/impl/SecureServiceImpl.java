package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.config.MmConfig;
import cn.finull.mm.server.service.SecureService;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.digest.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 11:11
 */
@Service
public class SecureServiceImpl implements SecureService {

    /**
     * 密码最小长度
     */
    private static final int PWD_MIN_LENGTH = 6;
    /**
     * 密码最长长度
     */
    private static final int PWD_MAX_LENGTH = 20;

    private final RSA rsa;

    public SecureServiceImpl(MmConfig mmConfig) {
        rsa = new RSA(mmConfig.getRsaPrivateKey(), mmConfig.getRsaPublicKey());
    }

    @Override
    public String rsaPublicKeyEncrypt(String content) {
        byte[] bs = rsa.encrypt(content, KeyType.PublicKey);
        return Base64.encode(bs);
    }

    @Override
    public String rsaPrivateKeyDecrypt(String base64) {
        return rsa.decryptStr(base64, KeyType.PrivateKey);
    }

    @Override
    public Boolean checkPwd(String pwd) {
        if (StrUtil.isBlank(pwd)) {
            return Boolean.FALSE;
        }

        if (pwd.length() < PWD_MIN_LENGTH || pwd.length() > PWD_MAX_LENGTH) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }

    @Override
    public String hashByBCrypt(String content) {
        return BCrypt.hashpw(content, BCrypt.gensalt());
    }

    @Override
    public Boolean checkByBCrypt(String content, String hashed) {
        return BCrypt.checkpw(content, hashed);
    }
}
