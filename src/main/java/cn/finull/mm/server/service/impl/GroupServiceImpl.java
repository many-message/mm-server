package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.Constant;
import cn.finull.mm.server.common.constant.RespCodeConstant;
import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import cn.finull.mm.server.common.enums.GroupStatusEnum;
import cn.finull.mm.server.dao.GroupMemberRepository;
import cn.finull.mm.server.dao.GroupRepository;
import cn.finull.mm.server.entity.Group;
import cn.finull.mm.server.entity.GroupMember;
import cn.finull.mm.server.param.GroupAddParam;
import cn.finull.mm.server.param.GroupUpdateParam;
import cn.finull.mm.server.service.GroupService;
import cn.finull.mm.server.util.RespUtil;
import cn.finull.mm.server.vo.GroupVO;
import cn.finull.mm.server.vo.resp.PageVO;
import cn.finull.mm.server.vo.resp.RespVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
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

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Override
    public RespVO<GroupVO> addGroup(GroupAddParam groupAddParam, Long userId) {
        Group group = new Group(groupAddParam.getGroupName(), groupAddParam.getGroupDesc(), buildGroupNum());
        groupRepository.save(group);

        GroupMember groupMember = new GroupMember(group.getGroupId(), userId, GroupMemberTypeEnum.OWNER);
        groupMemberRepository.save(groupMember);

        return RespUtil.OK(buildGroupVO(group));
    }

    private String buildGroupNum() {
        String groupNum = RandomUtil.randomNumbers(6);
        while (groupRepository.existsByGroupNum(groupNum)) {
            groupNum = RandomUtil.randomNumbers(6);
        }
        return groupNum;
    }

    @Override
    public RespVO<GroupVO> updateGroup(GroupUpdateParam groupUpdateParam, Long userId) {
        if (!groupMemberRepository.existsByGroupIdAndUserIdAndGroupMemberTypeNot(groupUpdateParam.getGroupId(), userId, GroupMemberTypeEnum.ORDINARY)) {
            return RespUtil.error(RespCodeConstant.ACCESS_FORBID, "权限不足！");
        }

        Group group = groupRepository.getOne(groupUpdateParam.getGroupId());
        BeanUtil.copyProperties(groupUpdateParam, group, Constant.NOT_COPY_NULL);
        group.setUpdateTime(new Date());
        groupRepository.save(group);

        return RespUtil.OK(buildGroupVO(group));
    }

    @Override
    public RespVO<List<GroupVO>> getGroups(Long userId) {
        List<Long> groupIds = groupMemberRepository.findAllByUserId(userId)
                .stream()
                .map(GroupMember::getGroupId)
                .collect(Collectors.toList());
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
        List<GroupVO> groups = groupRepository.findAllByGroupNumOrGroupNameLike(keyword, "%" + keyword + "%")
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespVO deleteGroup(Long groupId, Long userId) {
        if (!groupMemberRepository.existsByGroupIdAndUserIdAndGroupMemberType(groupId, userId, GroupMemberTypeEnum.OWNER)) {
            return RespUtil.error(RespCodeConstant.ACCESS_FORBID, "权限不足！");
        }

        groupRepository.deleteById(groupId);
        groupMemberRepository.deleteByGroupId(groupId);

        return RespUtil.OK();
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
            return RespUtil.error(RespCodeConstant.NOT_FOUND, "群不存在！");
        }

        Group group = groupOptional.get();
        group.setGroupStatus(groupStatus);
        group.setUpdateTime(new Date());
        groupRepository.save(group);

        return RespUtil.OK(buildGroupVO(group));
    }
}
