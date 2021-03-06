package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import cn.finull.mm.server.dao.GroupJoinInviteRepository;
import cn.finull.mm.server.dao.GroupMemberRepository;
import cn.finull.mm.server.dao.GroupRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.Group;
import cn.finull.mm.server.entity.GroupJoinInvite;
import cn.finull.mm.server.entity.GroupMember;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.param.GroupJoinInviteAddParam;
import cn.finull.mm.server.service.GroupJoinInviteService;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.vo.GroupJoinInviteVO;
import cn.finull.mm.server.common.vo.RespVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespVO sendGroupJoinInvite(GroupJoinInviteAddParam groupJoinInviteAddParam, Long userId) {

        if (CollUtil.isNotEmpty(groupJoinInviteAddParam.getInviteUserIds())) {
            groupJoinInviteAddParam.getInviteUserIds()
                    .forEach(inviteUserId ->
                            saveGroupJoinInvite(groupJoinInviteAddParam.getGroupId(), userId, inviteUserId));
        }

        return RespUtil.OK();
    }

    private void saveGroupJoinInvite(Long groupId, Long reqUserId, Long inviteUserId) {
        if (groupJoinInviteRepository.existsByGroupIdAndInviteUserId(groupId, inviteUserId)) {
            return;
        }
        if (groupMemberRepository.existsByGroupIdAndUserId(groupId, inviteUserId)) {
            return;
        }
        GroupJoinInvite groupJoinInvite = new GroupJoinInvite(groupId, reqUserId, inviteUserId);
        groupJoinInviteRepository.save(groupJoinInvite);
    }

    @Override
    public RespVO<List<GroupJoinInviteVO>> agreeGroupJoinInvite(Long groupJoinInviteId, Long userId) {
        if (!groupJoinInviteRepository.existsByGroupJoinInviteIdAndInviteUserId(groupJoinInviteId, userId)) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }

        GroupJoinInvite groupJoinInvite = groupJoinInviteRepository.getOne(groupJoinInviteId);

        if (!groupMemberRepository.existsByGroupIdAndUserId(groupJoinInvite.getGroupId(), groupJoinInvite.getInviteUserId())) {

            User user = userRepository.getOne(userId);
            GroupMember groupMember = new GroupMember(
                    groupJoinInvite.getGroupId(),
                    userId,
                    user.getNickname(),
                    GroupMemberTypeEnum.ORDINARY);

            groupMemberRepository.save(groupMember);
        }

        groupJoinInviteRepository.deleteById(groupJoinInviteId);

        return getGroupJoinInvites(userId);
    }

    @Override
    public RespVO<List<GroupJoinInviteVO>> getGroupJoinInvites(Long userId) {
        List<GroupJoinInviteVO> groupJoinInvites = groupJoinInviteRepository.findAllByInviteUserIdOrderByUpdateTimeDesc(userId)
                .stream()
                .map(this::buildGroupJoinInviteVO)
                .collect(Collectors.toList());
        return RespUtil.OK(groupJoinInvites);
    }

    @Override
    public RespVO<GroupJoinInviteVO> getGroupJoinInvite(Long groupJoinInviteId) {
        Optional<GroupJoinInvite> optional = groupJoinInviteRepository.findById(groupJoinInviteId);
        if (optional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "群聊邀请不存在！");
        }
        return RespUtil.OK(buildGroupJoinInviteVO(optional.get()));
    }

    private GroupJoinInviteVO buildGroupJoinInviteVO(GroupJoinInvite groupJoinInvite) {
        GroupJoinInviteVO groupJoinInviteVO = new GroupJoinInviteVO();

        Group group =groupRepository.getOne(groupJoinInvite.getGroupId());
        BeanUtil.copyProperties(group, groupJoinInviteVO);

        User reqUser = userRepository.getOne(groupJoinInvite.getReqUserId());
        BeanUtil.copyProperties(reqUser, groupJoinInviteVO);

        BeanUtil.copyProperties(groupJoinInvite, groupJoinInviteVO);

        return groupJoinInviteVO;
    }

    @Override
    public RespVO<List<GroupJoinInviteVO>> deleteGroupJoinInvite(Long groupJoinInviteId, Long userId) {
        if (!groupJoinInviteRepository.existsByGroupJoinInviteIdAndInviteUserId(groupJoinInviteId, userId)) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }

        groupJoinInviteRepository.deleteById(groupJoinInviteId);

        return getGroupJoinInvites(userId);
    }
}
