package cn.finull.mm.server.util;

import cn.finull.mm.server.common.constant.RespCodeConstant;
import cn.finull.mm.server.vo.resp.ErrorVO;
import cn.finull.mm.server.vo.resp.RespVO;

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
     * 判断响应是否成功
     * @param resp
     * @param <T>
     * @return
     */
    public static <T> Boolean isSuccess(RespVO<T> resp) {
        return RespCodeConstant.OK.equals(resp.getCode());
    }

    /**
     * 构建包含数据的成功响应
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RespVO<T> OK(T data) {
        return new RespVO<>(RespCodeConstant.OK, RespCodeConstant.OK, data);
    }

    /**
     * 构建不包含数据的成功响应
     * @param <T>
     * @return
     */
    public static <T> RespVO<T> OK() {
        return new RespVO<>(RespCodeConstant.OK, "SUCCESS", null);
    }

    /**
     * 构建包含详细错误的失败响应
     * @param code
     * @param message
     * @param errors
     * @param <T>
     * @return
     */
    public static <T> RespVO<T> error(String code, String message, List<ErrorVO> errors) {
        return new RespVO<>(code, message, errors);
    }

    /**
     * 构建不包含详细错误的失败响应
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> RespVO<T> error(String code, String message) {
        return new RespVO<>(code, message, null);
    }
}
