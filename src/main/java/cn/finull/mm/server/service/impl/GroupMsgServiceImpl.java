package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.Constant;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.common.vo.RespVO;
import cn.finull.mm.server.dao.GroupMemberRepository;
import cn.finull.mm.server.dao.GroupMsgRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.GroupMember;
import cn.finull.mm.server.entity.GroupMsg;
import cn.finull.mm.server.param.privates.GroupMsgAddPrivateParam;
import cn.finull.mm.server.service.GroupMsgService;
import cn.finull.mm.server.vo.GroupMsgVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-10 18:20
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupMsgServiceImpl implements GroupMsgService {

    private static final Integer PAGE_SIZE = Constant.PAGE_SIZE;

    private final GroupMsgRepository groupMsgRepository;
    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Override
    public RespVO addGroupMsg(GroupMsgAddPrivateParam param) {
        GroupMsg groupMsg = new GroupMsg();
        BeanUtil.copyProperties(param, groupMsg);
        groupMsg.setCreateTime(new Date());
        groupMsg.setUpdateTime(new Date());
        groupMsgRepository.save(groupMsg);
        return RespUtil.OK();
    }

    @Override
    public RespVO<List<GroupMsgVO>> getGroupMessages(Long groupId, Integer currentIndex) {
        List<GroupMsgVO> groupMessages = groupMsgRepository.findByGroupIdOrderByCreateTimeDesc(
                PageRequest.of(currentIndex / PAGE_SIZE, PAGE_SIZE), groupId)
                .stream()
                .map(this::buildGroupMsgVO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        int index = currentIndex % PAGE_SIZE;
        groupMessages = CollUtil.reverse(CollUtil.sub(groupMessages, index, groupMessages.size()));

        return RespUtil.OK(groupMessages);
    }

    private GroupMsgVO buildGroupMsgVO(GroupMsg groupMsg) {
        GroupMsgVO groupMsgVO = new GroupMsgVO();
        BeanUtil.copyProperties(groupMsg, groupMsgVO);

        String nickname = userRepository.getOne(groupMsg.getSendUserId()).getNickname();
        groupMsgVO.setNickname(nickname);

        Optional<GroupMember> optional = groupMemberRepository.findById(groupMsg.getSendGroupMemberId());
        if (optional.isEmpty()) {
            return null;
        }
        String groupMemberName = optional.get().getGroupMemberName();
        groupMsgVO.setGroupMemberName(groupMemberName);

        return groupMsgVO;
    }
}
