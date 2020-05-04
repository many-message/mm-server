package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.service.FriendReqService;
import cn.finull.mm.server.vo.FriendReqVO;
import cn.finull.mm.server.common.vo.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description
 * <p> 好友请求模块
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 11:08
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendReqController {

    private final FriendReqService friendReqService;

    /**
     * 获取所用好友请求
     * @param userId
     * @return
     */
    @GetMapping("/friend-reqs")
    public RespVO<List<FriendReqVO>> getFriendReqs(@RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return friendReqService.getFriendReqs(userId);
    }

    /**
     * 删除一个好友请求
     * @param friendReqId
     * @param userId
     * @return
     */
    @DeleteMapping("/friend-reqs/{friendReqId}")
    public RespVO deleteFriendReq(@PathVariable("friendReqId") Long friendReqId,
                                  @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return friendReqService.deleteFriendReq(friendReqId, userId);
    }
}
