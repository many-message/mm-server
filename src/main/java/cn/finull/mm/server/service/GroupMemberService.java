package cn.finull.mm.server.service;

import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import cn.finull.mm.server.vo.GroupMemberVO;
import cn.finull.mm.server.common.vo.RespVO;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-15 23:34
 */
public interface GroupMemberService {

    /**
     * 获取所有群成员
     * @param groupId
     * @return
     */
    RespVO<List<GroupMemberVO>> getGroupMembers(Long groupId);

    /**
     * 修改成员类型
     * @param groupMemberId
     * @param groupMemberType
     * @param userId
     * @return
     */
    RespVO<GroupMemberVO> updateGroupMemberType(Long groupMemberId, GroupMemberTypeEnum groupMemberType, Long userId);

    /**
     * 修改群备注名
     * @param groupMemberId
     * @param groupName
     * @param userId
     * @return
     */
    RespVO<GroupMemberVO> updateGroupMemberName(Long groupMemberId, String groupName, Long userId);

    /**
     * 删除一个成员
     * @param groupMemberId
     * @param userId
     * @return
     */
    RespVO deleteGroupMember(Long groupMemberId, Long userId);

    /**
     * 退群
     * @param groupId
     * @param userId
     * @return
     */
    RespVO leaveGroup(Long groupId, Long userId);

    // private

    /**
     * 获取一个群的所有用户
     * @param groupId
     * @return
     */
    RespVO<List<Long>> getUserIdsByGroupId(Long groupId);
}
