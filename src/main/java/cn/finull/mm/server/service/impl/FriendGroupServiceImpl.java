package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.RespCodeConstant;
import cn.finull.mm.server.dao.FriendGroupRepository;
import cn.finull.mm.server.dao.FriendRepository;
import cn.finull.mm.server.entity.FriendGroup;
import cn.finull.mm.server.param.FriendGroupUpdateParam;
import cn.finull.mm.server.service.FriendGroupService;
import cn.finull.mm.server.util.RespUtil;
import cn.finull.mm.server.vo.FriendGroupVO;
import cn.finull.mm.server.vo.resp.RespVO;
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
 * @date 2020-02-14 19:27
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendGroupServiceImpl implements FriendGroupService {

    private final FriendGroupRepository friendGroupRepository;
    private final FriendRepository friendRepository;

    @Override
    public RespVO<List<FriendGroupVO>> getFriendGroups(Long userId) {
        List<FriendGroupVO> friendGroups = friendGroupRepository
                .findAllByUserIdOrderByFriendGroupName(userId)
                .stream()
                .map(this::buildFriendGroup)
                .collect(Collectors.toList());
        return RespUtil.OK(friendGroups);
    }

    @Override
    public RespVO<FriendGroupVO> updateFriendGroup(FriendGroupUpdateParam friendGroupUpdateParam, Long userId) {
        Optional<FriendGroup> friendGroupOptional = friendGroupRepository
                .findByFriendGroupIdAndUserId(friendGroupUpdateParam.getFriendGroupId(), userId);
        if (friendGroupOptional.isEmpty()) {
            return RespUtil.error(RespCodeConstant.ACCESS_FORBID, "权限不足！");
        }

        FriendGroup friendGroup = friendGroupOptional.get();
        BeanUtil.copyProperties(friendGroupUpdateParam, friendGroup);
        friendGroup.setUpdateTime(new Date());
        friendGroupRepository.save(friendGroup);

        return RespUtil.OK(buildFriendGroup(friendGroup));
    }

    @Override
    public RespVO<FriendGroupVO> addFriendGroup(String friendGroupName, Long userId) {
        FriendGroup friendGroup = new FriendGroup(friendGroupName, userId);
        friendGroupRepository.save(friendGroup);
        return RespUtil.OK(buildFriendGroup(friendGroup));
    }

    private FriendGroupVO buildFriendGroup(FriendGroup friendGroup) {
        FriendGroupVO friendGroupVO = new FriendGroupVO();
        BeanUtil.copyProperties(friendGroup, friendGroup);
        return friendGroupVO;
    }

    @Override
    public RespVO deleteFriendGroup(Long friendGroupId, Long userId) {
        Optional<FriendGroup> friendGroupOptional = friendGroupRepository.findByFriendGroupIdAndUserId(friendGroupId, userId);
        if (friendGroupOptional.isEmpty()) {
            return RespUtil.error(RespCodeConstant.ACCESS_FORBID, "权限不足！");
        }

        if (friendRepository.countByFriendGroupId(friendGroupId) > 0) {
            return RespUtil.error(RespCodeConstant.ACCESS_FORBID, "改分组下存在好友，不能删除！");
        }

        friendGroupRepository.deleteById(friendGroupId);

        return RespUtil.OK();
    }
}
