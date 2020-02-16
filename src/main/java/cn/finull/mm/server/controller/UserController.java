package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.param.UserLoginParam;
import cn.finull.mm.server.param.UserRegisterParam;
import cn.finull.mm.server.param.UserUpdateParam;
import cn.finull.mm.server.param.UserUpdatePwdParam;
import cn.finull.mm.server.service.EmailService;
import cn.finull.mm.server.service.UserService;
import cn.finull.mm.server.vo.UserLoginVO;
import cn.finull.mm.server.vo.UserVO;
import cn.finull.mm.server.vo.resp.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description
 * <p> 用户模块
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 22:14
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    /**
     * 发送验证码到邮件
     * @param email 邮箱
     * @return
     */
    @GetMapping("/codes/{email}")
    public RespVO sendCode(@PathVariable("email") String email) {
        return emailService.sendCode(email);
    }

    /**
     * 用户注册
     * @param userRegisterParam
     * @return
     */
    @PostMapping("/register")
    public RespVO register(@Validated @RequestBody UserRegisterParam userRegisterParam) {
        return userService.register(userRegisterParam);
    }

    /**
     * 用户登录
     * @param userLoginParam
     * @return
     */
    @PostMapping("/login")
    public RespVO<UserLoginVO> login(@Validated @RequestBody UserLoginParam userLoginParam) {
        return userService.login(userLoginParam);
    }

    /**
     * 修改用户信息
     * @param userUpdateParam
     * @param userId
     * @return
     */
    @PutMapping("/users")
    public RespVO<UserVO> updateUserInfo(@Validated @RequestBody UserUpdateParam userUpdateParam,
                                         @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return userService.updateUserInfo(userUpdateParam, userId);
    }

    /**
     * 修改密码
     * @param userUpdatePwdParam
     * @return
     */
    @PatchMapping("/users/passwords")
    public RespVO updatePwd(@Validated @RequestBody UserUpdatePwdParam userUpdatePwdParam,
                            @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return userService.updatePwd(userUpdatePwdParam, userId);
    }

    /**
     * 搜索用户
     * @param keyword
     * @return
     */
    @GetMapping("/users/{keyword}")
    public RespVO<List<UserVO>> searchUser(@PathVariable("keyword") String keyword) {
        return userService.searchUser(keyword);
    }
}
