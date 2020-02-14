package cn.finull.mm.server.common.constant;

import cn.hutool.core.bean.copier.CopyOptions;

/**
 * Description
 * <p> 常量
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:26
 */
public class Constant {

    /**
     * BeanUtil拷贝时不拷贝null值
     */
    public static final CopyOptions NOT_COPY_NULL = new CopyOptions().ignoreNullValue();

    public static final String USER_CACHE_PREFIX = "user_";
    public static final String ADMIN_CACHE_PREFIX = "admin_";
}
