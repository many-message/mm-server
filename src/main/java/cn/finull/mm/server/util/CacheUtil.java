package cn.finull.mm.server.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.util.concurrent.TimeUnit;

/**
 * Description
 * <p> 缓存
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 14:01
 */
public final class CacheUtil {

    private CacheUtil() {}

    /**
     * 缓存id和token
     */
    private static final Cache<String, Long> TOKEN_ID_CACHE = Caffeine.newBuilder()
            .initialCapacity(10)
            .maximumSize(100)
            // 过期时间为最后一次访问后半小时
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build(token -> null);

    public static void putTokenAndId(String token, Long id) {
        TOKEN_ID_CACHE.put(token, id);
    }

    public static Long getIdByToken(String token) {
        return TOKEN_ID_CACHE.getIfPresent(token);
    }

    /**
     * 缓存邮箱和code，10分钟后过期
     */
    private static final Cache<String, String> EMAIL_CODE_CACHE = Caffeine.newBuilder()
            .initialCapacity(10)
            .maximumSize(100)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(key -> null);

    public static void putEmailAndCode(String email, String code) {
        EMAIL_CODE_CACHE.put(email, code);
    }

    public static String getCodeByEmail(String email) {
        return EMAIL_CODE_CACHE.getIfPresent(email);
    }
}
