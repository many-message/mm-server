package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import cn.finull.mm.server.dao.GroupJoinReqRepository;
import cn.finull.mm.server.dao.GroupMemberRepository;
import cn.finull.mm.server.dao.GroupRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.Group;
import cn.finull.mm.server.entity.GroupJoinReq;
import cn.finull.mm.server.entity.GroupMember;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.param.GroupJoinReqAddParam;
import cn.finull.mm.server.service.GroupJoinReqService;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.vo.GroupJoinReqVO;
import cn.finull.mm.server.common.vo.RespVO;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 11:24
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupJoinReqServiceImpl implements GroupJoinReqService {

    private final GroupJoinReqRepository groupJoinReqRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    public RespVO<List<Long>> sendGroupJoinReq(GroupJoinReqAddParam groupJoinReqAddParam, Long userId) {
        if (groupJoinReqRepository.existsByReqUserIdAndGroupId(userId, groupJoinReqAddParam.getGroupId())) {
            return RespUtil.error(RespCode.BAD_REQUEST, "请求已发送，请耐心等候！");
        }

        if (groupMemberRepository.existsByGroupIdAndUserId(groupJoinReqAddParam.getGroupId(), userId)) {
            return RespUtil.error(RespCode.FORBIDDEN, "已在群聊内！");
        }

        Optional<Group> optional = groupRepository.findById(groupJoinReqAddParam.getGroupId());
        if (optional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "群聊不存在！");
        }

        GroupJoinReq groupJoinReq = new GroupJoinReq(
                userId,
                groupJoinReqAddParam.getGroupId(),
                groupJoinReqAddParam.getReqMsg());
        groupJoinReqRepository.save(groupJoinReq);

        // 接收用户的ID集合
        List<Long> recvUserIds = groupMemberRepository.findAllByGroupIdAndGroupMemberTypeNot(
                groupJoinReqAddParam.getGroupId(), GroupMemberTypeEnum.ORDINARY)
                .stream()
                .map(GroupMember::getUserId)
                .collect(Collectors.toList());

        return RespUtil.OK(recvUserIds);
    }

    @Override
    public RespVO<List<GroupJoinReqVO>> agreeGroupJoinReq(Long groupJoinReqId, Long userId) {
        Optional<GroupJoinReq> optional = groupJoinReqRepository.findById(groupJoinReqId);

        if (optional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "请求不存在！");
        }

        GroupJoinReq groupJoinReq = optional.get();

        if (groupMemberRepository.existsByGroupIdAndUserIdAndGroupMemberType(
                groupJoinReq.getGroupId(), userId, GroupMemberTypeEnum.ORDINARY)) {
            return RespUtil.error(RespCode.FORBIDDEN);
        }

        // 如果不在群聊中
        if (!groupMemberRepository.existsByGroupIdAndUserId(
                groupJoinReq.getGroupId(), groupJoinReq.getReqUserId())) {

            User user = userRepository.getOne(groupJoinReq.getReqUserId());
            GroupMember groupMember = new GroupMember(
                    groupJoinReq.getGroupId(),
                    groupJoinReq.getReqUserId(),
                    user.getNickname(),
                    GroupMemberTypeEnum.ORDINARY);
            groupMemberRepository.save(groupMember);
        }

        groupJoinReqRepository.deleteByGroupId(groupJoinReqId);

        return getGroupJoinReqs(userId);
    }

    @Override
    public RespVO<List<GroupJoinReqVO>> deleteGroupJoinReq(Long groupJoinReqId, Long userId) {
        Optional<GroupJoinReq> optional = groupJoinReqRepository.findById(groupJoinReqId);

        if (optional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "请求不存在！");
        }

        GroupJoinReq groupJoinReq = optional.get();

        if (groupMemberRepository.existsByGroupIdAndUserIdAndGroupMemberTypeNot(
                groupJoinReq.getGroupId(), userId, GroupMemberTypeEnum.OWNER)) {
            return RespUtil.error(RespCode.FORBIDDEN);
        }

        groupJoinReqRepository.delete(groupJoinReq);

        return getGroupJoinReqs(userId);
    }

    @Override
    public RespVO<List<GroupJoinReqVO>> getGroupJoinReqs(Long userId) {
        List<Long> groupIds = groupMemberRepository.findByUserIdAndGroupMemberTypeNot(userId, GroupMemberTypeEnum.ORDINARY)
                .stream()
                .map(GroupMember::getGroupId)
                .collect(Collectors.toList());

        List<GroupJoinReqVO> groupJoinReqs = groupJoinReqRepository.findByGroupIdInOrderByCreateTimeDesc(groupIds)
                .stream()
                .map(this::buildGroupJoinReqPrivateVO)
                .collect(Collectors.toList());

        return RespUtil.OK(groupJoinReqs);
    }

    private GroupJoinReqVO buildGroupJoinReqPrivateVO(GroupJoinReq groupJoinReq) {
        GroupJoinReqVO groupJoinReqVO = new GroupJoinReqVO();

        User user = userRepository.getOne(groupJoinReq.getReqUserId());
        BeanUtil.copyProperties(user, groupJoinReqVO);

        Group group = groupRepository.getOne(groupJoinReq.getGroupId());
        BeanUtil.copyProperties(group, groupJoinReqVO);

        BeanUtil.copyProperties(groupJoinReq, groupJoinReqVO);

        return groupJoinReqVO;
    }
}
