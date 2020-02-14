package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.service.FriendService;
import cn.finull.mm.server.vo.FriendVO;
import cn.finull.mm.server.vo.resp.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description
 * <p> 好友模块
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 11:07
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendController {

    private final FriendService friendService;

    /**
     * 获取好友
     * @param friendGroupId
     * @param userId
     * @return
     */
    @GetMapping("/friends")
    public RespVO<List<FriendVO>> getFriends(@RequestParam(required = false) Long friendGroupId,
                                             @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return friendService.getFriends(friendGroupId, userId);
    }

    /**
     * 修改好友分组
     * @param friendId
     * @param friendGroupId
     * @param userId
     * @return
     */
    @PatchMapping("/friends/{friendId}/groups/{friendGroupId}")
    public RespVO updateFriendGroup(@PathVariable("friendId") Long friendId,
                                              @PathVariable("friendGroupId") Long friendGroupId,
                                              @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return friendService.updateFriendGroup(friendId, friendGroupId, userId);
    }

    /**
     * 修改好友备注
     * @param friendId
     * @param friendName
     * @param userId
     * @return
     */
    @PatchMapping("/friends/{friendId}")
    public RespVO<FriendVO> updateFriendName(@PathVariable("friendId") Long friendId,
                                             @RequestParam String friendName,
                                             @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return friendService.updateFriendName(friendId, friendName, userId);
    }

    /**
     * 删除一个好友
     * @param friendId
     * @param userId
     * @return
     */
    @DeleteMapping("/friends/{friendId}")
    public RespVO deleteFriend(@PathVariable("friendId") Long friendId,
                               @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return friendService.deleteFriend(friendId, userId);
    }
}
