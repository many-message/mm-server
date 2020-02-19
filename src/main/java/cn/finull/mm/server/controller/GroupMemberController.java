package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import cn.finull.mm.server.service.GroupMemberService;
import cn.finull.mm.server.vo.GroupMemberVO;
import cn.finull.mm.server.vo.resp.RespVO;
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
 * @date 2020-02-15 12:29
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    /**
     * 获取所有群成员
     * @param groupId
     * @return
     */
    @GetMapping("/groups/{groupId}/members")
    public RespVO<List<GroupMemberVO>> getGroupMembers(@PathVariable("groupId") Long groupId) {
        return groupMemberService.getGroupMembers(groupId);
    }

    /**
     * 修改群成员类型
     * @param groupMemberId
     * @param groupMemberType
     * @param userId
     * @return
     */
    @PatchMapping("/group-members/{groupMemberId}/types")
    public RespVO<GroupMemberVO> updateGroupMemberType(@PathVariable("groupMemberId") Long groupMemberId,
                                        @RequestParam GroupMemberTypeEnum groupMemberType,
                                        @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupMemberService.updateGroupMemberType(groupMemberId, groupMemberType, userId);
    }

    /**
     * 修改群备注名
     * @param groupMemberId
     * @param groupName
     * @param userId
     * @return
     */
    @PatchMapping("/group-members/{groupMemberId}/names")
    public RespVO<GroupMemberVO> updateGroupMemberName(@PathVariable("groupMemberId") Long groupMemberId,
                                                       @RequestParam String groupName,
                                                       @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupMemberService.updateGroupMemberName(groupMemberId, groupName, userId);
    }

    /**
     * 删除一个成员
     * @param groupMemberId
     * @param userId
     * @return
     */
    @DeleteMapping("/group-members/{groupMemberId}")
    public RespVO deleteGroupMember(@PathVariable("groupMemberId") Long groupMemberId,
                                    @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupMemberService.deleteGroupMember(groupMemberId, userId);
    }

    /**
     * 成员退出一个群
     * @param groupId
     * @param userId
     * @return
     */
    @DeleteMapping("/groups/{groupId}/members")
    public RespVO leaveGroup(@PathVariable("groupId") Long groupId,
                             @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupMemberService.leaveGroup(groupId, userId);
    }
}