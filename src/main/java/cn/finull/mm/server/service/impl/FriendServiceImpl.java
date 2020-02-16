package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.RespCodeConstant;
import cn.finull.mm.server.dao.FriendGroupRepository;
import cn.finull.mm.server.dao.FriendRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.Friend;
import cn.finull.mm.server.entity.FriendGroup;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.service.FriendService;
import cn.finull.mm.server.util.RespUtil;
import cn.finull.mm.server.vo.FriendVO;
import cn.finull.mm.server.vo.UserVO;
import cn.finull.mm.server.vo.privates.FriendDelPrivateVO;
import cn.finull.mm.server.vo.resp.RespVO;
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
 * @date 2020-02-14 20:20
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final FriendGroupRepository friendGroupRepository;
    private final UserRepository userRepository;

    @Override
    public RespVO<List<FriendVO>> getFriends(Long friendGroupId, Long userId) {
        List<Friend> friends;
        if (friendGroupId == null) {
            friends = friendRepository.findAllByUserIdOrderByFriendName(userId);
        } else {
            friends = friendRepository.findAllByFriendGroupIdAndUserIdOrderByFriendName(friendGroupId, userId);
        }

        List<FriendVO> friendResult = friends.stream().map(this::buildFriendVO).collect(Collectors.toList());

        return RespUtil.OK(friendResult);
    }

    @Override
    public RespVO<FriendVO> updateFriendName(Long friendId, String friendName, Long userId) {
        Optional<Friend> friendOptional = friendRepository.findByFriendIdAndUserId(friendId, userId);
        if (friendOptional.isEmpty()) {
            return RespUtil.error(RespCodeConstant.ACCESS_FORBID, "权限不足！");
        }

        Friend friend = friendOptional.get();
        friend.setFriendName(friendName);
        friend.setUpdateTime(new Date());
        friendRepository.save(friend);

        return RespUtil.OK(buildFriendVO(friend));
    }

    private FriendVO buildFriendVO(Friend friend) {
        FriendVO friendVO = new FriendVO();
        BeanUtil.copyProperties(friend, friendVO);

        User user = userRepository.getOne(friend.getFriendUserId());
        UserVO friendUser = new UserVO();
        BeanUtil.copyProperties(user, friendUser);

        friendVO.setFriendUser(friendUser);

        return friendVO;
    }

    @Override
    public RespVO updateFriendGroup(Long friendId, Long friendGroupId, Long userId) {
        Optional<Friend> friendOptional = friendRepository.findByFriendIdAndUserId(friendId, userId);
        Optional<FriendGroup> friendGroupOptional = friendGroupRepository.findByFriendGroupIdAndUserId(friendGroupId, userId);
        if (friendOptional.isEmpty() || friendGroupOptional.isEmpty()) {
            return RespUtil.error(RespCodeConstant.ACCESS_FORBID, "权限不足！");
        }

        Friend friend = friendOptional.get();
        friend.setFriendGroupId(friendGroupId);
        friend.setUpdateTime(new Date());
        friendRepository.save(friend);

        return RespUtil.OK();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespVO<FriendDelPrivateVO> deleteFriend(Long friendId, Long userId) {
        Optional<Friend> friendOptional = friendRepository.findByFriendIdAndUserId(friendId, userId);
        if (friendOptional.isEmpty()) {
            return RespUtil.error(RespCodeConstant.ACCESS_FORBID, "权限不足！");
        }

        Friend friend = friendOptional.get();
        friendRepository.deleteByUserIdAndFriendUserId(friend.getFriendUserId(), friend.getUserId());
        friendRepository.delete(friend);

        return RespUtil.OK(new FriendDelPrivateVO(userId, friend.getFriendUserId()));
    }
}
