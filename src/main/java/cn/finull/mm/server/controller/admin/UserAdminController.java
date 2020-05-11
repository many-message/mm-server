package cn.finull.mm.server.controller.admin;

import cn.finull.mm.server.common.enums.UserStatusEnum;
import cn.finull.mm.server.service.UserService;
import cn.finull.mm.server.vo.admin.UserAdminVO;
import cn.finull.mm.server.common.vo.PageVO;
import cn.finull.mm.server.common.vo.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 15:12
 */
@RestController
@RequestMapping("/admin/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserAdminController {

    private final UserService userService;

    /**
     * 查询所有用户
     * @param keyword
     * @return
     */
    @GetMapping("/users")
    public RespVO<PageVO<UserAdminVO>> getUsers(@RequestParam(required = false) String keyword,
                                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                                @RequestParam(required = false, defaultValue = "10") Integer size) {
        return userService.getUsers(keyword, page, size);
    }

    /**
     * 修改用户状态
     * @param userId
     * @param userStatus
     * @return
     */
    @PatchMapping("/users/{userId}/status")
    public RespVO<UserAdminVO> updateUserStatus(@PathVariable("userId") Long userId,
                                                @RequestParam UserStatusEnum userStatus) {
        return userService.updateUserStatus(userId, userStatus);
    }

    /**
     * 重置用户密码
     * @param userId
     * @return
     */
    @PatchMapping("/users/{userId}/passwords")
    public RespVO<String> resetUserPwd(@PathVariable("userId") Long userId) {
        return userService.resetUserPwd(userId);
    }
}
