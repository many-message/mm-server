package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.service.FriendService;
import cn.finull.mm.server.vo.FriendGroupVO;
import cn.finull.mm.server.common.vo.RespVO;
import cn.finull.mm.server.vo.FriendVO;
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
     * 查询好友详情
     * @param friendId 好友ID
     * @return
     */
    @GetMapping("/friends/{friendId}")
    public RespVO<FriendVO> getFriend(@PathVariable("friendId") Long friendId) {
        return friendService.getFriend(friendId);
    }

    /**
     * 修改好友分组
     * @param friendId
     * @param friendGroupId
     * @param userId
     * @return
     */
    @PatchMapping("/friends/{friendId}/groups/{friendGroupId}")
    public RespVO<List<FriendGroupVO>> updateFriendGroup(@PathVariable("friendId") Long friendId,
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
    public RespVO<List<FriendGroupVO>> updateFriendName(@PathVariable("friendId") Long friendId,
                                             @RequestParam String friendName,
                                             @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return friendService.updateFriendName(friendId, friendName, userId);
    }

    /**
     * 删除好友
     * @param friendId
     * @param userId
     * @return
     */
    @DeleteMapping("/friends/{friendId}")
    public RespVO<List<FriendGroupVO>> deleteFriend(@PathVariable("friendId") Long friendId,
                                                    @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return friendService.deleteFriend(friendId, userId);
    }
}
