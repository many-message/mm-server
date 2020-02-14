package cn.finull.mm.server;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 11:32
 */
@Slf4j
public class RsaUtil {

    @Test
    public void GeneratorKey() {
        KeyPair pair = SecureUtil.generateKeyPair("RSA");

        log.info("privateKey:[{}]", Base64.encode(pair.getPrivate().getEncoded()));
        log.info("publicKey:[{}]", Base64.encode(pair.getPublic().getEncoded()));
    }
}
