package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.Constant;
import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.common.enums.ChatTypeEnum;
import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import cn.finull.mm.server.common.enums.GroupStatusEnum;
import cn.finull.mm.server.dao.*;
import cn.finull.mm.server.entity.Group;
import cn.finull.mm.server.entity.GroupMember;
import cn.finull.mm.server.param.GroupAddParam;
import cn.finull.mm.server.param.GroupUpdateParam;
import cn.finull.mm.server.param.enums.GroupQueryTypeEnum;
import cn.finull.mm.server.service.GroupMemberService;
import cn.finull.mm.server.service.GroupService;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.vo.GroupDetailVO;
import cn.finull.mm.server.vo.GroupListVO;
import cn.finull.mm.server.vo.GroupMemberVO;
import cn.finull.mm.server.vo.GroupVO;
import cn.finull.mm.server.common.vo.PageVO;
import cn.finull.mm.server.common.vo.RespVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
 * @date 2020-02-15 13:36
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupServiceImpl implements GroupService {

    /**
     * 最大建群数量
     */
    private static final Integer MAX_ADD_GROUP_NUM = 3;

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final GroupJoinReqRepository groupJoinReqRepository;
    private final GroupJoinInviteRepository groupJoinInviteRepository;
    private final ChatRepository chatRepository;

    @Autowired
    private GroupMemberService groupMemberService;

    @Override
    public RespVO<List<GroupVO>> addGroup(GroupAddParam groupAddParam, Long userId) {
        if (groupMemberRepository.countByUserIdAndGroupMemberType(userId, GroupMemberTypeEnum.OWNER)
                >= MAX_ADD_GROUP_NUM) {
            return RespUtil.error(RespCode.FORBIDDEN, "您已达到创建群聊数量上限！");
        }

        Group group = new Group(groupAddParam.getGroupName(), groupAddParam.getGroupDesc(), buildGroupNum());
        groupRepository.save(group);

        String nickname = userRepository.getOne(userId).getNickname();

        GroupMember groupMember = new GroupMember(group.getGroupId(), userId, nickname, GroupMemberTypeEnum.OWNER);
        groupMemberRepository.save(groupMember);

        return getGroups(GroupQueryTypeEnum.MY_GROUP, userId);
    }

    private String buildGroupNum() {
        String groupNum = RandomUtil.randomNumbers(6);
        while (groupRepository.existsByGroupNum(groupNum)) {
            groupNum = RandomUtil.randomNumbers(6);
        }
        return groupNum;
    }

    @Override
    public RespVO<GroupDetailVO> updateGroup(GroupUpdateParam groupUpdateParam, Long userId) {
        if (!groupMemberRepository.existsByGroupIdAndUserIdAndGroupMemberTypeNot(groupUpdateParam.getGroupId(), userId, GroupMemberTypeEnum.ORDINARY)) {
            return RespUtil.error(RespCode.FORBIDDEN);
        }

        Group group = groupRepository.getOne(groupUpdateParam.getGroupId());
        BeanUtil.copyProperties(groupUpdateParam, group, Constant.NOT_COPY_NULL);
        group.setUpdateTime(new Date());
        groupRepository.save(group);

        return getGroupDetail(group.getGroupId(), userId);
    }

    @Override
    public RespVO<List<GroupVO>> getGroups(GroupQueryTypeEnum type, Long userId) {
        List<Long> groupIds;

        if (GroupQueryTypeEnum.MY_GROUP == type) {
            groupIds = groupMemberRepository.findByUserIdAndGroupMemberType(userId, GroupMemberTypeEnum.OWNER)
                    .stream()
                    .map(GroupMember::getGroupId)
                    .collect(Collectors.toList());
        } else {
            groupIds = groupMemberRepository.findByUserIdAndGroupMemberTypeNot(userId, GroupMemberTypeEnum.OWNER)
                    .stream()
                    .map(GroupMember::getGroupId)
                    .collect(Collectors.toList());
        }

        if (CollUtil.isEmpty(groupIds)) {
            return RespUtil.OK(List.of());
        }

        List<GroupVO> groups = groupRepository.findAllByGroupIdInOrderByCreateTimeDesc(groupIds)
                .stream()
                .map(this::buildGroupVO)
                .collect(Collectors.toList());

        return RespUtil.OK(groups);
    }

    @Override
    public RespVO<List<GroupVO>> searchGroups(String keyword) {
        keyword = "%" + keyword + "%";

        List<GroupVO> groups = groupRepository.findByGroupNumLikeOrGroupNameLike(keyword, keyword)
                .stream()
                .map(this::buildGroupVO)
                .collect(Collectors.toList());

        return RespUtil.OK(groups);
    }

    private GroupVO buildGroupVO(Group group) {
        GroupVO groupVO = new GroupVO();
        BeanUtil.copyProperties(group, groupVO);
        Long groupMemberNum = groupMemberRepository.countByGroupId(group.getGroupId());
        groupVO.setGroupMemberNum(groupMemberNum);
        return groupVO;
    }

    @Override
    public RespVO<GroupVO> getGroup(Long groupId, Long userId) {
        Optional<Group> optional = groupRepository.findById(groupId);

        if (optional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "群聊不存在！");
        }

        GroupVO groupVO = buildGroupVO(optional.get());

        Boolean join = groupMemberRepository.existsByGroupIdAndUserId(groupVO.getGroupId(), userId);
        groupVO.setJoin(join);

        return RespUtil.OK(groupVO);
    }

    @Override
    public RespVO<GroupDetailVO> getGroupDetail(Long groupId, Long userId) {
        Optional<Group> optional = groupRepository.findById(groupId);

        if (optional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "群聊不存在！");
        }

        GroupDetailVO groupDetailVO = new GroupDetailVO();

        BeanUtil.copyProperties(optional.get(), groupDetailVO);

        // 群成员
        List<GroupMemberVO> groupMembers = groupMemberService.getGroupMembers(groupId).getData();
        groupDetailVO.setGroupMembers(groupMembers);

        // 设置我在群聊中的类型和userId
        groupMembers.stream()
                .filter(groupMemberVO -> ObjectUtil.equal(groupMemberVO.getUserId(), userId))
                .findFirst()
                .ifPresent(groupMemberVO -> {
                    groupDetailVO.setMyGroupMemberType(groupMemberVO.getGroupMemberType());
                    groupDetailVO.setMyUserId(groupMemberVO.getUserId());
                });

        // 群所有者信息
        groupMembers.stream()
                .filter(groupMemberVO ->
                        ObjectUtil.equal(groupMemberVO.getGroupMemberType(), GroupMemberTypeEnum.OWNER))
                .findFirst()
                .ifPresent(groupMemberVO ->
                        groupDetailVO.setGroupOwnerName(groupMemberVO.getGroupMemberName()));

        return RespUtil.OK(groupDetailVO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespVO<GroupListVO> deleteGroup(Long groupId, Long userId) {
        if (!groupMemberRepository.existsByGroupIdAndUserIdAndGroupMemberType(
                groupId, userId, GroupMemberTypeEnum.OWNER)) {
            return RespUtil.error(RespCode.FORBIDDEN);
        }

        // 删除群
        groupRepository.deleteById(groupId);

        // 删除群成员
        groupMemberRepository.deleteByGroupId(groupId);

        // 删除入群请求
        groupJoinReqRepository.deleteByGroupId(groupId);

        // 删除入群邀请
        groupJoinInviteRepository.deleteByGroupId(groupId);

        // 删除群聊天列表
        chatRepository.deleteByChatObjIdAndChatType(groupId, ChatTypeEnum.GROUP);

        List<GroupVO> myGroups = getGroups(GroupQueryTypeEnum.MY_GROUP, userId).getData();
        List<GroupVO> joinGroups = getGroups(GroupQueryTypeEnum.MY_JOIN_GROUP, userId).getData();

        return RespUtil.OK(new GroupListVO(myGroups, joinGroups));
    }

    @Override
    public RespVO<PageVO<GroupVO>> getGroups(String keyword, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Group> groupPage;
        if (StrUtil.isBlank(keyword)) {
            groupPage = groupRepository.findAll(pageable);
        } else {
            groupPage = groupRepository.findAllByGroupNumOrGroupNameLike(keyword, "%" + keyword + "%", pageable);
        }

        PageVO<GroupVO> groupAdminPage = new PageVO<>(groupPage.getTotalElements(),
                groupPage.stream().map(this::buildGroupVO).collect(Collectors.toList()));

        return RespUtil.OK(groupAdminPage);
    }

    @Override
    public RespVO<GroupVO> updateGroupStatus(Long groupId, GroupStatusEnum groupStatus) {
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "群不存在！");
        }

        Group group = groupOptional.get();
        group.setGroupStatus(groupStatus);
        group.setUpdateTime(new Date());
        groupRepository.save(group);

        return RespUtil.OK(buildGroupVO(group));
    }
}
