package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.Constant;
import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.dao.FriendGroupRepository;
import cn.finull.mm.server.dao.FriendRepository;
import cn.finull.mm.server.dao.FriendReqRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.Friend;
import cn.finull.mm.server.entity.FriendReq;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.param.FriendReqAddParam;
import cn.finull.mm.server.param.FriendReqAgreeParam;
import cn.finull.mm.server.service.FriendReqService;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.vo.FriendReqVO;
import cn.finull.mm.server.common.vo.RespVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * @date 2020-02-14 17:00
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendReqServiceImpl implements FriendReqService {

    private final FriendReqRepository friendReqRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;
    private final FriendGroupRepository friendGroupRepository;

    @Override
    public RespVO sendFriendReq(FriendReqAddParam friendReqAddParam, Long userId) {
        if (Objects.equals(userId, friendReqAddParam.getRecvUserId())) {
            return RespUtil.error(RespCode.FORBIDDEN, "操作非法！");
        }

        if (friendReqRepository.existsByReqUserIdAndRecvUserId(userId, friendReqAddParam.getRecvUserId())) {
            return RespUtil.error(RespCode.BAD_REQUEST, "请求已发送，请耐心等待！");
        }

        if (friendRepository.existsByUserIdAndFriendUserId(userId, friendReqAddParam.getRecvUserId())) {
            return RespUtil.error(RespCode.FORBIDDEN, "已是好友关系！");
        }

        Optional<User> recUserOptional = userRepository.findById(friendReqAddParam.getRecvUserId());
        if (recUserOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "用户不存在！");
        }

        FriendReq friendReq = new FriendReq(
                userId,
                friendReqAddParam.getRecvUserId(),
                friendReqAddParam.getFriendGroupId(),
                friendReqAddParam.getReqMsg());

        friendReqRepository.save(friendReq);

        return RespUtil.OK();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespVO<List<FriendReqVO>> agreeFriendReq(FriendReqAgreeParam friendReqAgreeParam, Long userId) {
        Optional<FriendReq> optional = friendReqRepository.findByFriendReqIdAndRecvUserId(
                friendReqAgreeParam.getFriendReqId(), userId);

        if (optional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "请求不存在！");
        }

        FriendReq friendReq = optional.get();

        // 不是好友
        if (!friendRepository.existsByUserIdAndFriendUserId(userId, friendReq.getReqUserId())) {

            User reqUser = userRepository.getOne(friendReq.getReqUserId());
            User recvUser = userRepository.getOne(friendReq.getRecvUserId());

            // 判断好友分组是否存在
            if (!friendGroupRepository.existsById(friendReq.getFriendGroupId())) {
                friendGroupRepository.findByUserIdAndFriendGroupName(reqUser.getUserId(), Constant.FRIEND_GROUP_NAME)
                        .ifPresent(friendGroup -> friendReq.setFriendGroupId(friendGroup.getFriendGroupId()));
            }

            Friend reqFriend = new Friend(
                    reqUser.getUserId(),
                    recvUser.getUserId(),
                    friendReq.getFriendGroupId(),
                    recvUser.getNickname());

            Friend recvFriend = new Friend(
                    recvUser.getUserId(),
                    reqUser.getUserId(),
                    friendReqAgreeParam.getFriendGroupId(),
                    reqUser.getNickname());

            friendRepository.saveAll(List.of(reqFriend, recvFriend));
        }

        friendReqRepository.deleteById(friendReq.getFriendReqId());

        return getFriendReqs(userId);
    }

    @Override
    public RespVO<List<FriendReqVO>> getFriendReqs(Long userId) {
        List<FriendReq> reqs = friendReqRepository.findByRecvUserIdOrderByCreateTimeDesc(userId);

        List<FriendReqVO> friendReqs = reqs.stream().map(this::buildFriendReqVO).collect(Collectors.toList());

        return RespUtil.OK(friendReqs);
    }

    @Override
    public RespVO<FriendReqVO> getFriendReq(Long friendReqId) {
        Optional<FriendReq> optional = friendReqRepository.findById(friendReqId);
        if (optional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "请求不存在！");
        }
        return RespUtil.OK(buildFriendReqVO(optional.get()));
    }

    private FriendReqVO buildFriendReqVO(FriendReq friendReq) {
        // 好友请求信息
        FriendReqVO friendReqVO = new FriendReqVO();

        User reqUser = userRepository.getOne(friendReq.getReqUserId());

        // 请求用户信息
        BeanUtil.copyProperties(reqUser, friendReqVO);

        // 请求信息
        BeanUtil.copyProperties(friendReq, friendReqVO);

        return friendReqVO;
    }

    @Override
    public RespVO<List<FriendReqVO>> deleteFriendReq(Long friendReqId, Long userId) {
        Optional<FriendReq> friendReqOptional = friendReqRepository.findById(friendReqId);
        if (friendReqOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "好友请求不存在！");
        }

        FriendReq friendReq = friendReqOptional.get();
        if (ObjectUtil.notEqual(friendReq.getRecvUserId(), userId)) {
            return RespUtil.error(RespCode.FORBIDDEN);
        }

        friendReqRepository.delete(friendReq);

        return getFriendReqs(userId);
    }
}
