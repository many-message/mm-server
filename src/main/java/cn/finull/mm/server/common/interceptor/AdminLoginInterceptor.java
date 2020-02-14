package cn.finull.mm.server.common.interceptor;

import cn.finull.mm.server.common.constant.Constant;
import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.common.exception.AuthorException;
import cn.finull.mm.server.util.CacheUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description
 * <p> 对管理测做登录权限校验
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 13:05
 */
public class AdminLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(RequestConstant.X_ADMIN_ACCESS_TOKEN);
        if (StrUtil.isBlank(token)) {
            throw new AuthorException("token不能为空！");
        }

        Long adminId = CacheUtil.getIdByToken(Constant.ADMIN_CACHE_PREFIX + token);
        if (adminId == null) {
            throw new AuthorException("token不存在");
        }

        request.setAttribute(RequestConstant.ADMIN_ID, adminId);

        return true;
    }
}
