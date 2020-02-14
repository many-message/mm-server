package cn.finull.mm.server.service;

import cn.finull.mm.server.param.UserLoginParam;
import cn.finull.mm.server.param.UserRegisterParam;
import cn.finull.mm.server.param.UserUpdateParam;
import cn.finull.mm.server.param.UserUpdatePwdParam;
import cn.finull.mm.server.vo.UserLoginVO;
import cn.finull.mm.server.vo.UserVO;
import cn.finull.mm.server.vo.resp.RespVO;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 22:14
 */
public interface UserService {

    /**
     * 用户注册
     * @param userRegisterParam
     * @return
     */
    RespVO register(UserRegisterParam userRegisterParam);

    /**
     * 用户登录
     * @param userLoginParam
     * @return
     */
    RespVO<UserLoginVO> login(UserLoginParam userLoginParam);

    /**
     * 修改用户信息
     * @param userUpdateParam
     * @param userId
     * @return
     */
    RespVO<UserVO> updateUserInfo(UserUpdateParam userUpdateParam, Long userId);

    /**
     * 修改密码
     * @param userUpdatePwdParam
     * @param userId
     * @return
     */
    RespVO updatePwd(UserUpdatePwdParam userUpdatePwdParam, Long userId);
}
