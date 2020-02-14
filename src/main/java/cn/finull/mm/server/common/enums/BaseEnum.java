package cn.finull.mm.server.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:45
 */
public interface BaseEnum<T> {

    @JsonValue
    String getCode();
}
