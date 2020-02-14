package cn.finull.mm.server.common.exception;

/**
 * Description
 * <p> 权限校验异常
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:38
 */
public class AuthorException extends RuntimeException {
    public AuthorException(String message) {
        super(message);
    }
}
