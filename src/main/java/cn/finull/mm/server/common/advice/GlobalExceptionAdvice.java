package cn.finull.mm.server.common.advice;


import cn.finull.mm.server.common.constant.RespCodeConstant;
import cn.finull.mm.server.common.exception.AuthorException;
import cn.finull.mm.server.util.RespUtil;
import cn.finull.mm.server.vo.resp.ErrorVO;
import cn.finull.mm.server.vo.resp.RespVO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description
 * <p> 全局异常处理
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:26
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 权限校验失败
     * @param e
     * @return
     */
    @ExceptionHandler(AuthorException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RespVO handleAuthorException(AuthorException e) {
        return RespUtil.error(RespCodeConstant.AUTH_FAILED, RespCodeConstant.AUTH_FAILED_MSG, buildError(e));
    }

    /**
     * 缺少参数
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespVO handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return RespUtil.error(RespCodeConstant.PARAM_MISSING, RespCodeConstant.PARAM_MISSING_MSG, buildError(e));
    }

    /**
     * 参数校验失败
     * @param e
     * @return
     */
    @ExceptionHandler({ValidationException.class, ServletRequestBindingException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespVO handleInvalidServletRequestParameterException(Exception e) {
        return RespUtil.error(RespCodeConstant.PARAM_INVALID, RespCodeConstant.PARAM_INVALID_MSG, buildError(e));
    }

    /**
     * 参数校验失败
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespVO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return RespUtil.error(RespCodeConstant.PARAM_INVALID, RespCodeConstant.PARAM_INVALID_MSG, buildFieldsError(e.getBindingResult()));
    }

    private static List<ErrorVO> buildFieldsError(BindingResult result) {
        List<ErrorVO> vos = new ArrayList<>();
        result.getFieldErrors().forEach((fieldError) -> {
            vos.add(new ErrorVO(fieldError.getField(), fieldError.getDefaultMessage()));
        });
        return vos;
    }

    /**
     * 请求方法不支持
     * @param e
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public RespVO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return RespUtil.error(RespCodeConstant.METHOD_NOT_SUPPORT, RespCodeConstant.METHOD_NOT_SUPPORT_MSG, buildError(e));
    }

    /**
     * 内部错误
     * @param e
     * @return
     */
    @ExceptionHandler({Throwable.class, Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespVO handleException(Exception e) {
        return RespUtil.error(RespCodeConstant.SYS_ERROR, RespCodeConstant.SYS_ERROR_MSG, buildError(e));
    }

    private List<ErrorVO> buildError(Throwable e) {
        return List.of(new ErrorVO(e.getClass().getName(), e.getMessage()));
    }
}
