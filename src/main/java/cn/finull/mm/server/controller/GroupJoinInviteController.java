package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.param.GroupJoinInviteAddParam;
import cn.finull.mm.server.service.GroupJoinInviteService;
import cn.finull.mm.server.vo.GroupJoinInviteVO;
import cn.finull.mm.server.common.vo.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-15 12:28
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupJoinInviteController {

    private final GroupJoinInviteService groupJoinInviteService;

    /**
     * 邀请好友加入群聊
     * @param groupJoinInviteAddParam
     * @param userId
     * @return
     */
    @PostMapping("/group-join-invites")
    public RespVO sendGroupJoinInvite(@Validated @RequestBody GroupJoinInviteAddParam groupJoinInviteAddParam,
                                      @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupJoinInviteService.sendGroupJoinInvite(groupJoinInviteAddParam, userId);
    }

    /**
     * 同意加入群聊
     * @param groupJoinInviteId
     * @param userId
     * @return
     */
    @PatchMapping("/group-join-invites/{groupJoinInviteId}")
    public RespVO<List<GroupJoinInviteVO>> agreeGroupJoinInvite(@PathVariable("groupJoinInviteId") Long groupJoinInviteId,
                                                                @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupJoinInviteService.agreeGroupJoinInvite(groupJoinInviteId, userId);
    }

    /**
     * 获取所有入群邀请
     * @param userId
     * @return
     */
    @GetMapping("/group-join-invites")
    public RespVO<List<GroupJoinInviteVO>> getGroupJoinInvites(@RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupJoinInviteService.getGroupJoinInvites(userId);
    }

    /**
     * 删除入群邀请
     * @param groupJoinInviteId
     * @param userId
     * @return
     */
    @DeleteMapping("/group-join-invites/{groupJoinInviteId}")
    public RespVO<List<GroupJoinInviteVO>> deleteGroupJoinInvite(@PathVariable("groupJoinInviteId") Long groupJoinInviteId,
                                        @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupJoinInviteService.deleteGroupJoinInvite(groupJoinInviteId, userId);
    }
}
