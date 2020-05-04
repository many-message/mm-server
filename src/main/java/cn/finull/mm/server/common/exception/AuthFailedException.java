package cn.finull.mm.server.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description
 * <p> 权限校验异常
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:38
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthFailedException extends RuntimeException {
    public AuthFailedException(String message, Object...args) {
        super(String.format(message, args));
    }
}
