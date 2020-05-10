package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.service.GroupMsgService;
import cn.finull.mm.server.service.MsgService;
import cn.finull.mm.server.common.vo.RespVO;
import cn.finull.mm.server.vo.GroupMsgVO;
import cn.finull.mm.server.vo.MsgVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 16:12
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MsgController {

    private final MsgService msgService;
    private final GroupMsgService groupMsgService;

    /**
     * 签收消息
     * @param sendUserId
     * @param recvUserId
     * @return
     */
    @PatchMapping("/messages/{sendUserId}")
    public RespVO recvMessage(@PathVariable("sendUserId") Long sendUserId,
                              @RequestAttribute(RequestConstant.USER_ID) Long recvUserId) {
        return msgService.recvMessage(sendUserId, recvUserId);
    }

    /**
     * 查询消息列表
     * @param sendUserId
     * @param currentIndex
     * @param userId
     * @return
     */
    @GetMapping("/{sendUserId}/messages")
    public RespVO<List<MsgVO>> getMessages(@PathVariable("sendUserId") Long sendUserId,
                                           @RequestParam(required = false, defaultValue = "0") Integer currentIndex,
                                           @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return msgService.getMessages(sendUserId, currentIndex, userId);
    }

    /**
     * 查询群聊消息
     * @param groupId
     * @param currentIndex
     * @return
     */
    @GetMapping("/groups/{groupId}/messages")
    public RespVO<List<GroupMsgVO>> getGroupMessages(@PathVariable("groupId") Long groupId,
                                                     @RequestParam(required = false, defaultValue = "0") Integer currentIndex) {
        return groupMsgService.getGroupMessages(groupId, currentIndex);
    }
}
