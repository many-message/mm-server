package cn.finull.mm.server.common.util;

import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.common.vo.ErrorVO;
import cn.finull.mm.server.common.vo.RespVO;

import java.util.List;

/**
 * Description
 * <p> 响应消息工具类
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:26
 */
public final class RespUtil {

    private RespUtil() {}

    /**
     * 构建不包含数据的成功响应
     * @param <T>
     * @return
     */
    public static <T> RespVO<T> OK() {
        return new RespVO<>(RespCode.OK.getCode(), RespCode.OK.getMessage(), null);
    }

    /**
     * 构建包含数据的成功响应
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RespVO<T> OK(T data) {
        return new RespVO<>(RespCode.OK.getCode(), RespCode.OK.getMessage(), data);
    }

    public static <T> RespVO<T> error(RespCode code) {
        return new RespVO<>(code.getCode(), code.getMessage(), null);
    }

    public static <T> RespVO<T> error(RespCode code, String message) {
        return new RespVO<>(code.getCode(), message, null);
    }

    public static <T> RespVO<T> error(RespCode code, List<ErrorVO> errors) {
        return new RespVO<>(code.getCode(), code.getMessage(), errors);
    }

    public static <T> RespVO<T> error(RespCode code, String message, List<ErrorVO> errors) {
        return new RespVO<>(code.getCode(), message, errors);
    }

    public static <T> RespVO<T> error(RespCode code, Exception e) {
        return new RespVO<>(code.getCode(), code.getMessage(), List.of(new ErrorVO(e.getClass().toString(), e.getMessage())));
    }

    public static <T> RespVO<T> error(RespCode code, String message, Exception e) {
        return new RespVO<>(code.getCode(), message, List.of(new ErrorVO(e.getClass().toString(), e.getMessage())));
    }
}
