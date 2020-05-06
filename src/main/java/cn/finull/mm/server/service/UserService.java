package cn.finull.mm.server.service;

import cn.finull.mm.server.common.enums.UserStatusEnum;
import cn.finull.mm.server.param.UserLoginParam;
import cn.finull.mm.server.param.UserRegisterParam;
import cn.finull.mm.server.param.UserUpdateParam;
import cn.finull.mm.server.param.UserUpdatePwdParam;
import cn.finull.mm.server.vo.UserLoginVO;
import cn.finull.mm.server.vo.UserVO;
import cn.finull.mm.server.vo.admin.UserAdminVO;
import cn.finull.mm.server.common.vo.PageVO;
import cn.finull.mm.server.common.vo.RespVO;

import java.util.List;

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

    /**
     * 搜索用户
     * @param keyword
     * @return
     */
    RespVO<List<UserVO>> searchUser(String keyword);

    /**
     * 查询用户详情
     * @param userId
     * @param currentUserId 当前登录用户ID
     * @return
     */
    RespVO<UserVO> getUser(Long userId, Long currentUserId);

    // admin

    /**
     * 获取所有用户列表
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    RespVO<PageVO<UserAdminVO>> getUsers(String keyword, Integer page, Integer size);

    /**
     * 修改用户状态
     * @param userId
     * @param userStatus
     * @return
     */
    RespVO<UserAdminVO> updateUserStatus(Long userId, UserStatusEnum userStatus);

    /**
     * 重置用户密码
     * @param userId
     * @return
     */
    RespVO resetUserPwd(Long userId);
}
