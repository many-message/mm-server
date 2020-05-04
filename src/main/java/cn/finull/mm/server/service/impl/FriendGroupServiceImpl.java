package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.Constant;
import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.dao.FriendGroupRepository;
import cn.finull.mm.server.dao.FriendRepository;
import cn.finull.mm.server.entity.FriendGroup;
import cn.finull.mm.server.param.FriendGroupUpdateParam;
import cn.finull.mm.server.service.FriendGroupService;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.service.FriendService;
import cn.finull.mm.server.vo.FriendGroupVO;
import cn.finull.mm.server.common.vo.RespVO;
import cn.finull.mm.server.vo.FriendVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
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

    /**
     * 最大分组数量
     */
    private static final Integer MAX_FRIEND_GROUP_NUM = 10;

    private final FriendGroupRepository friendGroupRepository;
    private final FriendRepository friendRepository;

    @Autowired
    private FriendService friendService;

    @Override
    public RespVO<List<FriendGroupVO>> getFriendGroups(Long userId) {
        List<FriendGroupVO> friendGroups = friendGroupRepository
                .findAllByUserIdOrderByCreateTimeAsc(userId)
                .stream()
                .map(this::buildFriendGroup)
                .collect(Collectors.toList());
        return RespUtil.OK(friendGroups);
    }

    @Override
    public RespVO<List<FriendGroupVO>> updateFriendGroup(FriendGroupUpdateParam friendGroupUpdateParam, Long userId) {
        Optional<FriendGroup> friendGroupOptional = friendGroupRepository
                .findByFriendGroupIdAndUserId(friendGroupUpdateParam.getFriendGroupId(), userId);

        if (friendGroupOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "分组不存在！");
        }

        FriendGroup friendGroup = friendGroupOptional.get();

        if (Constant.FRIEND_GROUP_NAME.equals(friendGroup.getFriendGroupName())) {
            return RespUtil.error(RespCode.FORBIDDEN,
                    String.format("不能修改[%s]分组！", Constant.FRIEND_GROUP_NAME));
        }

        if (!StrUtil.equals(friendGroupUpdateParam.getFriendGroupName(), friendGroup.getFriendGroupName())
                && friendGroupRepository.existsByUserIdAndFriendGroupName(userId, friendGroupUpdateParam.getFriendGroupName())) {
            return RespUtil.error(RespCode.BAD_REQUEST,
                    String.format("分组名[%s]已存在！", friendGroupUpdateParam.getFriendGroupName()));
        }

        BeanUtil.copyProperties(friendGroupUpdateParam, friendGroup);
        friendGroup.setUpdateTime(new Date());
        friendGroupRepository.save(friendGroup);

        return getFriendGroups(userId);
    }

    @Override
    public RespVO<List<FriendGroupVO>> addFriendGroup(String friendGroupName, Long userId) {
        if (friendGroupRepository.existsByUserIdAndFriendGroupName(userId, friendGroupName)) {
            return RespUtil.error(RespCode.BAD_REQUEST,
                    String.format("分组名[%s]已存在！", friendGroupName));
        }

        if (friendGroupRepository.countByUserId(userId) >= MAX_FRIEND_GROUP_NUM) {
            return RespUtil.error(RespCode.FORBIDDEN, "新建分组个数已超出上线！");
        }

        FriendGroup friendGroup = new FriendGroup(friendGroupName, userId);
        friendGroupRepository.save(friendGroup);

        return getFriendGroups(userId);
    }

    private FriendGroupVO buildFriendGroup(FriendGroup friendGroup) {
        FriendGroupVO friendGroupVO = new FriendGroupVO();

        BeanUtil.copyProperties(friendGroup, friendGroupVO);

        // 查询该分组下的所有好友
        List<FriendVO> friends = friendService.getFriends(friendGroup.getFriendGroupId());
        friendGroupVO.setFriends(friends);

        return friendGroupVO;
    }

    @Override
    public RespVO<List<FriendGroupVO>> deleteFriendGroup(Long friendGroupId, Long userId) {
        Optional<FriendGroup> friendGroupOptional = friendGroupRepository.findByFriendGroupIdAndUserId(friendGroupId, userId);
        if (friendGroupOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }

        if (Constant.FRIEND_GROUP_NAME.equals(friendGroupOptional.get().getFriendGroupName())) {
            return RespUtil.error(RespCode.FORBIDDEN,
                    String.format("不能删除[%s]分组！", Constant.FRIEND_GROUP_NAME));
        }

        if (friendRepository.countByFriendGroupId(friendGroupId) > 0) {
            return RespUtil.error(RespCode.FORBIDDEN, "该分组下存在好友，不能删除！");
        }

        friendGroupRepository.deleteById(friendGroupId);

        return getFriendGroups(userId);
    }
}
