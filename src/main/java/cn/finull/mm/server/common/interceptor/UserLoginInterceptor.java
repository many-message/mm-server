package cn.finull.mm.server.common.interceptor;

import cn.finull.mm.server.common.constant.Constant;
import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.common.exception.AuthFailedException;
import cn.finull.mm.server.util.CacheUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description
 * <p> 对客户侧做登录权限校验
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 13:04
 */
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(RequestConstant.X_USER_ACCESS_TOKEN);
        if (StrUtil.isBlank(token)) {
            throw new AuthFailedException("token不能为空！");
        }

        Long userId = CacheUtil.getIdByToken(Constant.USER_CACHE_PREFIX + token);
        if (userId == null) {
            throw new AuthFailedException("token不存在");
        }

        request.setAttribute(RequestConstant.USER_ID, userId);

        return true;
    }
}
