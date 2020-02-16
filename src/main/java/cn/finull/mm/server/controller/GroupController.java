package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.param.GroupAddParam;
import cn.finull.mm.server.param.GroupUpdateParam;
import cn.finull.mm.server.service.GroupService;
import cn.finull.mm.server.vo.GroupVO;
import cn.finull.mm.server.vo.resp.RespVO;
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
     * @return
     */
    @PostMapping("/groups")
    public RespVO<GroupVO> addGroup(@Validated @RequestBody GroupAddParam groupAddParam,
                                    @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupService.addGroup(groupAddParam, userId);
    }

    /**
     * 修改群
     * @param groupUpdateParam
     * @return
     */
    @PutMapping("/groups")
    public RespVO<GroupVO> updateGroup(@Validated @RequestBody GroupUpdateParam groupUpdateParam,
                                       @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupService.updateGroup(groupUpdateParam, userId);
    }

    /**
     * 获取自己的所有群
     * @param userId
     * @return
     */
    @GetMapping("/groups")
    public RespVO<List<GroupVO>> getGroups(@RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupService.getGroups(userId);
    }

    /**
     * 根据群号搜索群
     * @param keyword
     * @return
     */
    @GetMapping("/groups/{keyword}")
    public RespVO<List<GroupVO>> searchGroups(@PathVariable("keyword") String keyword) {
        return groupService.searchGroups(keyword);
    }

    /**
     * 删除群
     * @param groupId
     * @param userId
     * @return
     */
    @DeleteMapping("/groups/{groupId}")
    public RespVO deleteGroup(@PathVariable("groupId") Long groupId,
                              @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupService.deleteGroup(groupId, userId);
    }
}
