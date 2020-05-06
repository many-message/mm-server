package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.param.GroupAddParam;
import cn.finull.mm.server.param.GroupUpdateParam;
import cn.finull.mm.server.param.enums.GroupQueryTypeEnum;
import cn.finull.mm.server.service.GroupService;
import cn.finull.mm.server.vo.GroupDetailVO;
import cn.finull.mm.server.vo.GroupListVO;
import cn.finull.mm.server.vo.GroupVO;
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
 * @date 2020-02-15 12:27
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupController {

    private final GroupService groupService;

    /**
     * 新建一个群
     * @param groupAddParam
     * @return 我的群聊
     */
    @PostMapping("/groups")
    public RespVO<List<GroupVO>> addGroup(@Validated @RequestBody GroupAddParam groupAddParam,
                                    @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupService.addGroup(groupAddParam, userId);
    }

    /**
     * 修改群
     * @param groupUpdateParam
     * @return
     */
    @PutMapping("/groups")
    public RespVO<GroupDetailVO> updateGroup(@Validated @RequestBody GroupUpdateParam groupUpdateParam,
                                       @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupService.updateGroup(groupUpdateParam, userId);
    }

    /**
     * 获取自己的所有群
     * @param type 1-我的群聊；2-我加入的群聊
     * @param userId 当前登录用户ID
     * @return
     */
    @GetMapping("/groups")
    public RespVO<List<GroupVO>> getGroups(@RequestParam GroupQueryTypeEnum type,
                                           @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupService.getGroups(type, userId);
    }

    /**
     * 根据群号、群名称模糊搜索群
     * @param keyword
     * @return
     */
    @GetMapping("/groups/{keyword}/search")
    public RespVO<List<GroupVO>> searchGroups(@PathVariable("keyword") String keyword) {
        return groupService.searchGroups(keyword);
    }

    /**
     * 查询群聊详情
     * @param groupId
     * @param userId
     * @return
     */
    @GetMapping("/groups/{groupId}")
    public RespVO<GroupVO> getGroup(@PathVariable("groupId") Long groupId,
                                    @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupService.getGroup(groupId, userId);
    }

    /**
     * 查询群详情，包括群成员信息
     * @param groupId
     * @param userId
     * @return
     */
    @GetMapping("/groups/{groupId}/detail")
    public RespVO<GroupDetailVO> getGroupDetail(@PathVariable("groupId") Long groupId,
                                                @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupService.getGroupDetail(groupId, userId);
    }

    /**
     * 删除群
     * @param groupId
     * @param userId
     * @return
     */
    @DeleteMapping("/groups/{groupId}")
    public RespVO<GroupListVO> deleteGroup(@PathVariable("groupId") Long groupId,
                                           @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupService.deleteGroup(groupId, userId);
    }
}
