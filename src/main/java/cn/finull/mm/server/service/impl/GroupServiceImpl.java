package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.Constant;
import cn.finull.mm.server.common.constant.RespCodeConstant;
import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import cn.finull.mm.server.dao.GroupMemberRepository;
import cn.finull.mm.server.dao.GroupRepository;
import cn.finull.mm.server.entity.Group;
import cn.finull.mm.server.entity.GroupMember;
import cn.finull.mm.server.param.GroupAddParam;
import cn.finull.mm.server.param.GroupUpdateParam;
import cn.finull.mm.server.service.GroupService;
import cn.finull.mm.server.util.RespUtil;
import cn.finull.mm.server.vo.GroupVO;
import cn.finull.mm.server.vo.resp.RespVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

    private GroupVO buildGroupVO(Group group) {
        GroupVO groupVO = new GroupVO();
        BeanUtil.copyProperties(group, groupVO);
        Long groupMemberNum = groupMemberRepository.countByGroupId(group.getGroupId());
        groupVO.setGroupMemberNum(groupMemberNum);
        return groupVO;
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
}
