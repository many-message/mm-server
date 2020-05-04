package cn.finull.mm.server.common.constant;

import cn.finull.mm.server.common.exception.SystemException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-03 14:56
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum RespCode {
    /**
     * 响应编码
     */
    OK("200", "成功！"),
    BAD_REQUEST("400", "参数错误！"),
    UNAUTHORIZED("401", "认证失败！"),
    FORBIDDEN("403", "权限不足！"),
    NOT_FOUND("404", "资源不存在！"),
    METHOD_NOT_ALLOWED("405", "方法不支持！"),
    UNSUPPORTED_MEDIA_TYPE("415", "媒体类型不支持！"),
    TOO_MANY_REQUESTS("429", "请求过长！"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误！"),
    SERVICE_UNAVAILABLE("503", "服务过载！");

    private String code;
    private String message;

    @JsonCreator
    public static RespCode parse(String code) {
        for (RespCode item : values()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        throw new SystemException("业务响应码[%s]无法识别！", code);
    }
}
