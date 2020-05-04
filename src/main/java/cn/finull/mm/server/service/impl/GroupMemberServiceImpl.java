package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import cn.finull.mm.server.dao.GroupMemberRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.GroupMember;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.service.GroupMemberService;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.vo.GroupMemberVO;
import cn.finull.mm.server.vo.UserVO;
import cn.finull.mm.server.common.vo.RespVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public RespVO<List<GroupMemberVO>> getGroupMembers(Long groupId) {
        List<GroupMemberVO> groupMembers = groupMemberRepository.findAllByGroupIdOrderByGroupMemberType(groupId)
                .stream()
                .map(this::buildGroupMemberVO)
                .collect(Collectors.toList());
        return RespUtil.OK(groupMembers);
    }

    @Override
    public RespVO<GroupMemberVO> updateGroupMemberType(Long groupMemberId, GroupMemberTypeEnum groupMemberType, Long userId) {
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

        return RespUtil.OK(buildGroupMemberVO(groupMember));
    }

    @Override
    public RespVO<GroupMemberVO> updateGroupMemberName(Long groupMemberId, String groupName, Long userId) {
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

        return RespUtil.OK(buildGroupMemberVO(groupMember));
    }

    private GroupMemberVO buildGroupMemberVO(GroupMember groupMember) {
        GroupMemberVO groupMemberVO = new GroupMemberVO();
        BeanUtil.copyProperties(groupMember, groupMemberVO);

        User user = userRepository.getOne(groupMember.getUserId());
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        groupMemberVO.setGroupMemberUser(userVO);

        return groupMemberVO;
    }

    @Override
    public RespVO deleteGroupMember(Long groupMemberId, Long userId) {
        Optional<GroupMember> groupMemberOptional = groupMemberRepository.findById(groupMemberId);
        if (groupMemberOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "成员不存在！");
        }

        GroupMember groupMember = groupMemberOptional.get();
        if (ObjectUtil.equal(groupMember.getUserId(), userId) || !groupMemberRepository.existsByGroupIdAndUserIdAndGroupMemberTypeNot(groupMember.getGroupId(), userId, GroupMemberTypeEnum.ORDINARY)) {
            return RespUtil.error(RespCode.FORBIDDEN);
        }

        groupMemberRepository.delete(groupMember);

        return RespUtil.OK();
    }

    @Override
    public RespVO leaveGroup(Long groupId, Long userId) {
        groupMemberRepository.deleteByGroupIdAndUserId(groupId, userId);
        return RespUtil.OK();
    }

    @Override
    public RespVO<List<Long>> getUserIdsByGroupId(Long groupId) {
        List<Long> userIds = groupMemberRepository.findAllByGroupIdOrderByGroupMemberType(groupId)
                .parallelStream()
                .map(GroupMember::getUserId)
                .collect(Collectors.toList());
        return RespUtil.OK(userIds);
    }
}
