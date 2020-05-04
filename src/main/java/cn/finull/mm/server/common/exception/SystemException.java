package cn.finull.mm.server.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-03 15:03
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemException extends RuntimeException {

    public SystemException(String message, Object...args) {
        super(String.format(message, args));
    }
}
