package cn.finull.mm.server.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * Description
 * <p> 响应消息格式
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:26
 */
@Data
@AllArgsConstructor
public class RespVO<T> {

    private String code;

    private String message;

    private List<ErrorVO> errors;

    private T data;

    public RespVO(String code, String message, List<ErrorVO> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    public RespVO(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
