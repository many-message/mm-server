package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.param.FriendGroupUpdateParam;
import cn.finull.mm.server.service.FriendGroupService;
import cn.finull.mm.server.vo.FriendGroupPreviewVO;
import cn.finull.mm.server.vo.FriendGroupVO;
import cn.finull.mm.server.common.vo.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description
 * <p> 好友分组模块
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 11:09
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendGroupController {

    private final FriendGroupService friendGroupService;

    /**
     * 获取所有分组
     * @param userId
     * @return
     */
    @GetMapping("/friend-groups")
    public RespVO<List<FriendGroupVO>> getFriendGroups(@RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return friendGroupService.getFriendGroups(userId);
    }

    /**
     * 修改分组
     * @param friendGroupUpdateParam
     * @param userId
     * @return
     */
    @PutMapping("/friend-groups")
    public RespVO<List<FriendGroupVO>> updateFriendGroup(@Validated @RequestBody FriendGroupUpdateParam friendGroupUpdateParam,
                                                   @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return friendGroupService.updateFriendGroup(friendGroupUpdateParam, userId);
    }

    /**
     * 添加一个好友分组
     * @param friendGroupName
     * @param userId
     * @return
     */
    @PostMapping("/friend-groups/{friendGroupName}")
    public RespVO<List<FriendGroupVO>> addFriendGroup(@PathVariable("friendGroupName") String friendGroupName,
                                                @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return friendGroupService.addFriendGroup(friendGroupName, userId);
    }

    /**
     * 删除一个好友分组
     * @param friendGroupId 分组ID
     * @param userId
     * @return
     */
    @DeleteMapping("/friend-groups/{friendGroupId}")
    public RespVO<List<FriendGroupVO>> deleteFriendGroup(@PathVariable("friendGroupId") Long friendGroupId,
                                    @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return friendGroupService.deleteFriendGroup(friendGroupId, userId);
    }

    /**
     * 预览分组列表
     * @param userId 当前登录用户ID
     * @return
     */
    @GetMapping("/friend-groups/preview")
    public RespVO<List<FriendGroupPreviewVO>> getFriendGroupPreviews(
            @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return friendGroupService.getFriendGroupPreviews(userId);
    }
}
