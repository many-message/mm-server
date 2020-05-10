package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.common.enums.ChatTypeEnum;
import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import cn.finull.mm.server.dao.ChatRepository;
import cn.finull.mm.server.dao.GroupMemberRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.GroupMember;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.param.enums.GroupQueryTypeEnum;
import cn.finull.mm.server.service.GroupMemberService;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.service.GroupService;
import cn.finull.mm.server.vo.*;
import cn.finull.mm.server.common.vo.RespVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-15 23:34
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupMemberServiceImpl implements GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @Autowired
    private GroupService groupService;

    @Override
    public RespVO<List<GroupMemberVO>> getGroupMembers(Long groupId) {
        List<GroupMemberVO> groupMembers = groupMemberRepository.findAllByGroupIdOrderByGroupMemberType(groupId)
                .stream()
                .map(this::buildGroupMemberVO)
                .collect(Collectors.toList());
        return RespUtil.OK(groupMembers);
    }

    @Override
    public RespVO<List<GroupMemberVO>> updateGroupMemberType(Long groupMemberId, GroupMemberTypeEnum groupMemberType, Long userId) {
        Optional<GroupMember> groupMemberOptional = groupMemberRepository.findById(groupMemberId);
        if (groupMemberOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "成员不存在！");
        }

        GroupMember groupMember = groupMemberOptional.get();
        if (!groupMemberRepository.existsByGroupIdAndUserIdAndGroupMemberType(groupMember.getGroupId(), userId, GroupMemberTypeEnum.OWNER)) {
            return RespUtil.error(RespCode.FORBIDDEN);
        }

        groupMember.setGroupMemberType(groupMemberType);
        groupMember.setUpdateTime(new Date());
        groupMemberRepository.save(groupMember);

        return getGroupMembers(groupMember.getGroupId());
    }

    @Override
    public RespVO<GroupDetailVO> updateGroupMemberName(Long groupMemberId, String groupName, Long userId) {
        Optional<GroupMember> groupMemberOptional = groupMemberRepository.findById(groupMemberId);
        if (groupMemberOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "成员不存在！");
        }

        GroupMember groupMember = groupMemberOptional.get();
        if (ObjectUtil.notEqual(groupMember.getUserId(), userId) &&
                !groupMemberRepository.existsByGroupIdAndUserIdAndGroupMemberTypeNot(groupMember.getGroupId(), userId, GroupMemberTypeEnum.ORDINARY)) {
            return RespUtil.error(RespCode.FORBIDDEN);
        }

        groupMember.setGroupMemberName(groupName);
        groupMember.setUpdateTime(new Date());
        groupMemberRepository.save(groupMember);

        return groupService.getGroupDetail(groupMember.getGroupId(), userId);
    }

    private GroupMemberVO buildGroupMemberVO(GroupMember groupMember) {
        GroupMemberVO groupMemberVO = new GroupMemberVO();

        User user = userRepository.getOne(groupMember.getUserId());
        BeanUtil.copyProperties(user, groupMemberVO);

        BeanUtil.copyProperties(groupMember, groupMemberVO);

        return groupMemberVO;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespVO<List<GroupMemberVO>> deleteGroupMember(Long groupMemberId, Long userId) {
        Optional<GroupMember> groupMemberOptional = groupMemberRepository.findById(groupMemberId);
        if (groupMemberOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "成员不存在！");
        }

        GroupMember groupMember = groupMemberOptional.get();
        if (ObjectUtil.equal(groupMember.getUserId(), userId)
                || groupMember.getGroupMemberType() == GroupMemberTypeEnum.OWNER
                || groupMemberRepository.existsByGroupIdAndUserIdAndGroupMemberType(
                        groupMember.getGroupId(), userId, GroupMemberTypeEnum.ORDINARY)) {
            return RespUtil.error(RespCode.FORBIDDEN);
        }

        // 删除成员
        groupMemberRepository.delete(groupMember);

        // 删除聊天列表
        chatRepository.deleteByUserIdAndChatObjIdAndChatType(
                groupMember.getUserId(), groupMember.getGroupId(), ChatTypeEnum.GROUP);

        return getGroupMembers(groupMember.getGroupId());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespVO<GroupListVO> leaveGroup(Long groupId, Long userId) {
        if (groupMemberRepository.existsByGroupIdAndUserIdAndGroupMemberType(
                groupId, userId, GroupMemberTypeEnum.OWNER)) {
            return RespUtil.error(RespCode.FORBIDDEN);
        }

        // 删除群成员
        groupMemberRepository.deleteByGroupIdAndUserId(groupId, userId);

        // 删除聊天列表
        chatRepository.deleteByUserIdAndChatObjIdAndChatType(userId, groupId, ChatTypeEnum.GROUP);

        List<GroupVO> myGroups = groupService.getGroups(GroupQueryTypeEnum.MY_GROUP, userId).getData();
        List<GroupVO> joinGroups = groupService.getGroups(GroupQueryTypeEnum.MY_JOIN_GROUP, userId).getData();

        return RespUtil.OK(new GroupListVO(myGroups, joinGroups));
    }

    @Override
    public RespVO<List<Long>> getUserIdsByGroupId(Long groupId) {
        List<Long> userIds = groupMemberRepository.findAllByGroupIdOrderByGroupMemberType(groupId)
                .parallelStream()
                .map(GroupMember::getUserId)
                .collect(Collectors.toList());
        return RespUtil.OK(userIds);
    }

    @Override
    public RespVO<GroupMemberVO> getGroupMemberUser(Long groupId, Long userId) {
        Optional<GroupMember> optional = groupMemberRepository.findByGroupIdAndUserId(groupId, userId);
        if (optional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "抱歉，您已不在群聊中！");
        }
        return RespUtil.OK(buildGroupMemberVO(optional.get()));
    }
}
