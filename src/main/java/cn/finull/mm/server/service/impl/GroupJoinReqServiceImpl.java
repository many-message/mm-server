package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.common.enums.GroupJoinReqStatusEnum;
import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import cn.finull.mm.server.dao.GroupJoinReqRepository;
import cn.finull.mm.server.dao.GroupMemberRepository;
import cn.finull.mm.server.dao.GroupRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.Group;
import cn.finull.mm.server.entity.GroupJoinReq;
import cn.finull.mm.server.entity.GroupMember;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.param.privates.GroupJoinReqAddPrivateParam;
import cn.finull.mm.server.service.GroupJoinReqService;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.vo.GroupVO;
import cn.finull.mm.server.vo.UserVO;
import cn.finull.mm.server.vo.GroupJoinReqVO;
import cn.finull.mm.server.common.vo.RespVO;
import cn.hutool.core.bean.BeanUtil;
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
    public RespVO<GroupJoinReqVO> joinGroup(GroupJoinReqAddPrivateParam groupJoinReqAddPrivateParam) {
        if (groupMemberRepository.existsByGroupIdAndUserId(groupJoinReqAddPrivateParam.getGroupId(), groupJoinReqAddPrivateParam.getReqUserId())) {
            return RespUtil.error(RespCode.FORBIDDEN, "已在群内！");
        }

        Optional<Group> groupOptional = groupRepository.findById(groupJoinReqAddPrivateParam.getGroupId());
        Optional<User> userOptional = userRepository.findById(groupJoinReqAddPrivateParam.getReqUserId());
        if (groupOptional.isEmpty() || userOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }

        Group group = groupOptional.get();
        User user = userOptional.get();

        GroupJoinReq groupJoinReq = new GroupJoinReq(user.getUserId(), group.getGroupId());
        Optional<GroupJoinReq> groupJoinReqOptional = groupJoinReqRepository.findByGroupIdAndReqUserIdAndAndGroupJoinReqStatus(
                group.getGroupId(), user.getUserId(), GroupJoinReqStatusEnum.REQ);
        if (groupJoinReqOptional.isPresent()) {
            groupJoinReq = groupJoinReqOptional.get();
        }
        groupJoinReq.setReqMsg(groupJoinReqAddPrivateParam.getReqMsg());

        groupJoinReqRepository.save(groupJoinReq);

        return RespUtil.OK(buildGroupJoinReqPrivateVO(groupJoinReq, group, user));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespVO<GroupJoinReqVO> updateGroupReqStatus(Long groupJoinReqId, GroupJoinReqStatusEnum groupJoinReqStatus) {
        Optional<GroupJoinReq> groupJoinReqOptional = groupJoinReqRepository.findById(groupJoinReqId);
        if (groupJoinReqOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }

        GroupJoinReq groupJoinReq = groupJoinReqOptional.get();
        groupJoinReq.setGroupJoinReqStatus(groupJoinReqStatus);
        groupJoinReq.setUpdateTime(new Date());
        groupJoinReqRepository.save(groupJoinReq);

        if (groupJoinReqStatus == GroupJoinReqStatusEnum.AGREE) {
            joinGroup(groupJoinReq);
        }

        return RespUtil.OK(buildGroupJoinReqPrivateVO(groupJoinReq));
    }

    private void joinGroup(GroupJoinReq groupJoinReq) {
        if (!groupMemberRepository.existsByGroupIdAndUserId(groupJoinReq.getGroupId(), groupJoinReq.getReqUserId())) {
            GroupMember groupMember = new GroupMember(groupJoinReq.getGroupId(), groupJoinReq.getReqUserId(), GroupMemberTypeEnum.ORDINARY);
            groupMemberRepository.save(groupMember);
        }
    }

    @Override
    public RespVO<List<GroupJoinReqVO>> getGroupJoinReqs(Long userId) {
        List<Long> groupIds = groupMemberRepository.findByUserIdAndGroupMemberTypeNot(userId, GroupMemberTypeEnum.ORDINARY)
                .stream()
                .map(GroupMember::getGroupId)
                .collect(Collectors.toList());

        List<GroupJoinReqVO> groupJoinReqs = groupJoinReqRepository.findAllByGroupIdInAndGroupJoinReqStatus(groupIds, GroupJoinReqStatusEnum.REQ)
                .stream()
                .map(this::buildGroupJoinReqPrivateVO)
                .collect(Collectors.toList());

        return RespUtil.OK(groupJoinReqs);
    }

    private GroupJoinReqVO buildGroupJoinReqPrivateVO(GroupJoinReq groupJoinReq) {
        Group group = groupRepository.getOne(groupJoinReq.getGroupId());
        User user = userRepository.getOne(groupJoinReq.getReqUserId());
        return buildGroupJoinReqPrivateVO(groupJoinReq, group, user);
    }

    private GroupJoinReqVO buildGroupJoinReqPrivateVO(GroupJoinReq groupJoinReq, Group group, User user) {
        GroupJoinReqVO groupJoinReqVO = new GroupJoinReqVO();
        BeanUtil.copyProperties(groupJoinReq, groupJoinReqVO);

        GroupVO groupVO = new GroupVO();
        BeanUtil.copyProperties(group, groupVO);
        groupJoinReqVO.setGroup(groupVO);

        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        groupJoinReqVO.setReqUser(userVO);

        List<Long> recUserIds = groupMemberRepository.findAllByGroupIdAndGroupMemberTypeNot(group.getGroupId(), GroupMemberTypeEnum.ORDINARY)
                .stream()
                .map(GroupMember::getUserId)
                .collect(Collectors.toList());
        groupJoinReqVO.setRecUserIds(recUserIds);

        return groupJoinReqVO;
    }
}
