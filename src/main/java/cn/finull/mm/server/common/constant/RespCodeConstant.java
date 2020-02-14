package cn.finull.mm.server.common.constant;

/**
 * Description
 * <p> 业务响应码常量
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:26
 */
public class RespCodeConstant {
    /**
     * 成功
     */
    public static final String OK = "00";
    public static final String OK_MSG = "SUCCESS";
    /**
     * 参数校验失败
     */
    public static final String PARAM_INVALID = "40001";
    public static final String PARAM_INVALID_MSG = "请求参数错误！";
    /**
     * 参数缺失
     */
    public static final String PARAM_MISSING = "40002";
    public static final String PARAM_MISSING_MSG = "请求参数为必填！";
    /**
     * 验证失败
     */
    public static final String AUTH_FAILED = "40101";
    public static final String AUTH_FAILED_MSG = "会话失效！";
    /**
     * 禁止访问
     */
    public static final String ACCESS_FORBID = "40301";
    public static final String ACCESS_FORBID_MSG = "请求被禁止访问！";
    /**
     * 资源不存在
     */
    public static final String NOT_FOUND = "40401";
    public static final String NOT_FOUND_MSG = "请求资源不存在！";
    /**
     * 请求方法不支持
     */
    public static final String METHOD_NOT_SUPPORT = "40501";
    public static final String METHOD_NOT_SUPPORT_MSG = "请求方法不被支持！";
    /**
     * 系统内部错误
     */
    public static final String SYS_ERROR = "50001";
    public static final String SYS_ERROR_MSG = "系统内部错误！";
}
