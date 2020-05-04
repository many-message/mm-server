package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.common.enums.GroupJoinInviteStatusEnum;
import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import cn.finull.mm.server.dao.GroupJoinInviteRepository;
import cn.finull.mm.server.dao.GroupMemberRepository;
import cn.finull.mm.server.dao.GroupRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.Group;
import cn.finull.mm.server.entity.GroupJoinInvite;
import cn.finull.mm.server.entity.GroupMember;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.param.privates.GroupJoinInviteAddPrivateParam;
import cn.finull.mm.server.service.GroupJoinInviteService;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.vo.GroupJoinInviteVO;
import cn.finull.mm.server.vo.GroupVO;
import cn.finull.mm.server.vo.UserVO;
import cn.finull.mm.server.common.vo.RespVO;
import cn.hutool.core.bean.BeanUtil;
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
 * @date 2020-02-16 13:28
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupJoinInviteServiceImpl implements GroupJoinInviteService {

    private final GroupJoinInviteRepository groupJoinInviteRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Override
    public RespVO<GroupJoinInviteVO> inviteJoinGroup(GroupJoinInviteAddPrivateParam groupJoinInviteAddPrivateParam) {
        if (groupMemberRepository.existsByGroupIdAndUserId(groupJoinInviteAddPrivateParam.getGroupId(), groupJoinInviteAddPrivateParam.getInviteUserId())) {
            return RespUtil.error(RespCode.FORBIDDEN, "已在群中！");
        }

        Optional<Group> groupOptional = groupRepository.findById(groupJoinInviteAddPrivateParam.getGroupId());
        Optional<User> reqUserOptional = userRepository.findById(groupJoinInviteAddPrivateParam.getReqUserId());
        Optional<User> inviteUserOptional = userRepository.findById(groupJoinInviteAddPrivateParam.getInviteUserId());
        if (groupOptional.isEmpty() || reqUserOptional.isEmpty() || inviteUserOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }

        Group group = groupOptional.get();
        User reqUser = reqUserOptional.get();
        User inviteUser = inviteUserOptional.get();

        GroupJoinInvite groupJoinInvite = new GroupJoinInvite(group.getGroupId(), reqUser.getUserId(), inviteUser.getUserId());
        groupJoinInviteRepository.save(groupJoinInvite);

        return RespUtil.OK(buildGroupJoinInviteVO(groupJoinInvite, group, reqUser, inviteUser.getUserId()));
    }

    @Override
    public RespVO<GroupJoinInviteVO> updateGroupInviteStatus(Long groupJoinInviteId, GroupJoinInviteStatusEnum groupJoinInviteStatus) {
        Optional<GroupJoinInvite> groupJoinInviteOptional = groupJoinInviteRepository.findById(groupJoinInviteId);
        if (groupJoinInviteOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }

        GroupJoinInvite groupJoinInvite = groupJoinInviteOptional.get();
        groupJoinInvite.setGroupJoinInviteStatus(groupJoinInviteStatus);
        groupJoinInvite.setUpdateTime(new Date());
        groupJoinInviteRepository.save(groupJoinInvite);

        if (groupJoinInviteStatus == GroupJoinInviteStatusEnum.AGREE) {
            joinGroup(groupJoinInvite);
        }

        return RespUtil.OK(buildGroupJoinInviteVO(groupJoinInvite));
    }

    private void joinGroup(GroupJoinInvite groupJoinInvite) {
        if (!groupMemberRepository.existsByGroupIdAndUserId(groupJoinInvite.getGroupId(), groupJoinInvite.getInviteUserId())) {
            GroupMember groupMember = new GroupMember(groupJoinInvite.getGroupId(), groupJoinInvite.getInviteUserId(), GroupMemberTypeEnum.ORDINARY);
            groupMemberRepository.save(groupMember);
        }
    }

    @Override
    public RespVO<List<GroupJoinInviteVO>> getGroupJoinInvites(Long userId) {
        List<GroupJoinInviteVO> groupJoinInvites = groupJoinInviteRepository.findAllByInviteUserIdOrderByUpdateTimeDesc(userId)
                .stream()
                .map(this::buildGroupJoinInviteVO)
                .collect(Collectors.toList());
        return RespUtil.OK(groupJoinInvites);
    }

    private GroupJoinInviteVO buildGroupJoinInviteVO(GroupJoinInvite groupJoinInvite) {
        Group group =groupRepository.getOne(groupJoinInvite.getGroupId());
        User reqUser = userRepository.getOne(groupJoinInvite.getReqUserId());
        return buildGroupJoinInviteVO(groupJoinInvite, group, reqUser, groupJoinInvite.getInviteUserId());
    }

    private GroupJoinInviteVO buildGroupJoinInviteVO(GroupJoinInvite groupJoinInvite, Group group, User reqUser, Long inviteUserId) {
        GroupJoinInviteVO groupJoinInviteVO = new GroupJoinInviteVO();
        BeanUtil.copyProperties(groupJoinInvite, groupJoinInviteVO);

        GroupVO groupVO = new GroupVO();
        BeanUtil.copyProperties(group, groupVO);
        groupJoinInviteVO.setGroup(groupVO);

        UserVO reqUserVO = new UserVO();
        BeanUtil.copyProperties(reqUser, reqUserVO);
        groupJoinInviteVO.setReqUser(reqUserVO);

        groupJoinInviteVO.setInviteUserId(inviteUserId);

        return groupJoinInviteVO;
    }

    @Override
    public RespVO deleteGroupJoinInvite(Long groupJoinInviteId, Long userId) {
        if (!groupJoinInviteRepository.existsByGroupJoinInviteIdAndInviteUserId(groupJoinInviteId, userId)) {
            return RespUtil.error(RespCode.FORBIDDEN);
        }
        groupJoinInviteRepository.deleteById(groupJoinInviteId);
        return RespUtil.OK();
    }
}
