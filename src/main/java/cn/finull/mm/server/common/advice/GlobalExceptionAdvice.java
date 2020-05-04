package cn.finull.mm.server.common.advice;

import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.common.exception.AccessForbidException;
import cn.finull.mm.server.common.exception.AuthFailedException;
import cn.finull.mm.server.common.exception.NotFoundException;
import cn.finull.mm.server.common.exception.SystemException;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.common.vo.ErrorVO;
import cn.finull.mm.server.common.vo.RespVO;
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

    private List<ErrorVO> buildError(Throwable e) {
        return List.of(new ErrorVO(e.getClass().toString(), e.getMessage()));
    }

    /**
     * 缺少参数 400
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespVO handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return RespUtil.error(RespCode.BAD_REQUEST, buildError(e));
    }

    /**
     * 参数校验失败 400
     * @param e
     * @return
     */
    @ExceptionHandler({ValidationException.class, ServletRequestBindingException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespVO handleInvalidServletRequestParameterException(Exception e) {
        return RespUtil.error(RespCode.BAD_REQUEST, buildError(e));
    }

    /**
     * 参数校验失败 400
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RespVO handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return RespUtil.error(RespCode.BAD_REQUEST, buildFieldsError(e.getBindingResult()));
    }

    private static List<ErrorVO> buildFieldsError(BindingResult result) {
        List<ErrorVO> vos = new ArrayList<>();
        result.getFieldErrors().forEach((fieldError) -> {
            vos.add(new ErrorVO(fieldError.getField(), fieldError.getDefaultMessage()));
        });
        return vos;
    }

    /**
     * 权限校验失败 401
     * @param e
     * @return
     */
    @ExceptionHandler(AuthFailedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public RespVO handleAuthorException(AuthFailedException e) {
        return RespUtil.error(RespCode.UNAUTHORIZED, e.getMessage());
    }

    /**
     * 权限不足 403
     * @param e
     * @return
     */
    @ExceptionHandler(AccessForbidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public RespVO handleAccessForbidException(AccessForbidException e) {
        return RespUtil.error(RespCode.FORBIDDEN, e.getMessage());
    }

    /**
     * 资源不存在 404
     * @param e
     * @return
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public RespVO handleNotFoundException(NotFoundException e) {
        return RespUtil.error(RespCode.NOT_FOUND, e.getMessage());
    }

    /**
     * 请求方法不支持 405
     * @param e
     * @return
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public RespVO handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return RespUtil.error(RespCode.METHOD_NOT_ALLOWED, buildError(e));
    }

    /**
     * 内部错误 500
     * @param e
     * @return
     */
    @ExceptionHandler({SystemException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RespVO handleException(Exception e) {
        return RespUtil.error(RespCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
