package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.dao.FriendGroupRepository;
import cn.finull.mm.server.dao.FriendRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.Friend;
import cn.finull.mm.server.entity.FriendGroup;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.service.FriendGroupService;
import cn.finull.mm.server.service.FriendService;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.vo.FriendGroupVO;
import cn.finull.mm.server.vo.FriendVO;
import cn.finull.mm.server.vo.privates.FriendDelPrivateVO;
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
 * @date 2020-02-14 20:20
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final FriendGroupRepository friendGroupRepository;
    private final UserRepository userRepository;

    @Autowired
    private FriendGroupService friendGroupService;

    @Override
    public List<FriendVO> getFriends(Long friendGroupId) {
        return friendRepository.findByFriendGroupIdOrderByCreateTimeDesc(friendGroupId)
                .stream()
                .map(this::buildFriendVO)
                .collect(Collectors.toList());
    }

    @Override
    public RespVO<FriendVO> getFriend(Long friendId) {
        Optional<Friend> optional = friendRepository.findById(friendId);

        if (optional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "好友不存在！");
        }

        return RespUtil.OK(buildFriendVO(optional.get()));
    }

    @Override
    public RespVO<List<FriendGroupVO>> updateFriendName(Long friendId, String friendName, Long userId) {
        Optional<Friend> friendOptional = friendRepository.findByFriendIdAndUserId(friendId, userId);
        if (friendOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "好友不存在！");
        }

        Friend friend = friendOptional.get();
        friend.setFriendName(friendName);
        friend.setUpdateTime(new Date());
        friendRepository.save(friend);

        return friendGroupService.getFriendGroups(userId);
    }

    private FriendVO buildFriendVO(Friend friend) {
        FriendVO friendVO = new FriendVO();
        BeanUtil.copyProperties(friend, friendVO);

        User user = userRepository.getOne(friend.getFriendUserId());
        BeanUtil.copyProperties(user, friendVO);

        return friendVO;
    }

    @Override
    public RespVO<List<FriendGroupVO>> updateFriendGroup(Long friendId, Long friendGroupId, Long userId) {
        Optional<Friend> friendOptional = friendRepository.findByFriendIdAndUserId(friendId, userId);
        Optional<FriendGroup> friendGroupOptional = friendGroupRepository.findByFriendGroupIdAndUserId(friendGroupId, userId);

        if (friendOptional.isEmpty() || friendGroupOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }

        Friend friend = friendOptional.get();
        friend.setFriendGroupId(friendGroupId);
        friend.setUpdateTime(new Date());

        friendRepository.save(friend);

        return friendGroupService.getFriendGroups(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespVO<FriendDelPrivateVO> deleteFriend(Long friendId, Long userId) {
        Optional<Friend> friendOptional = friendRepository.findByFriendIdAndUserId(friendId, userId);
        if (friendOptional.isEmpty()) {
            return RespUtil.error(RespCode.FORBIDDEN);
        }

        Friend friend = friendOptional.get();
        friendRepository.deleteByUserIdAndFriendUserId(friend.getFriendUserId(), friend.getUserId());
        friendRepository.delete(friend);

        return RespUtil.OK(new FriendDelPrivateVO(userId, friend.getFriendUserId()));
    }
}
