package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.RespCodeConstant;
import cn.finull.mm.server.common.enums.FriendReqStatusEnum;
import cn.finull.mm.server.dao.FriendRepository;
import cn.finull.mm.server.dao.FriendReqRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.Friend;
import cn.finull.mm.server.entity.FriendReq;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.param.privates.FriendReqAddPrivateParam;
import cn.finull.mm.server.service.FriendReqService;
import cn.finull.mm.server.util.RespUtil;
import cn.finull.mm.server.vo.FriendReqVO;
import cn.finull.mm.server.vo.UserVO;
import cn.finull.mm.server.vo.resp.RespVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
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
 * @date 2020-02-14 17:00
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendReqServiceImpl implements FriendReqService {

    private final FriendReqRepository friendReqRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Override
    public RespVO<FriendReqVO> addFriendReq(FriendReqAddPrivateParam friendReqAddPrivateParam) {
        if (friendRepository.existsByUserIdAndFriendUserId(friendReqAddPrivateParam.getReqUserId(), friendReqAddPrivateParam.getRecUserId())) {
            return RespUtil.error(RespCodeConstant.ACCESS_FORBID, "已是好友关系！");
        }

        Optional<User> reqUserOptional = userRepository.findById(friendReqAddPrivateParam.getReqUserId());
        Optional<User> recUserOptional = userRepository.findById(friendReqAddPrivateParam.getRecUserId());

        if (reqUserOptional.isEmpty() || recUserOptional.isEmpty()) {
            return RespUtil.error(RespCodeConstant.NOT_FOUND, "用户不存在！");
        }

        User reqUser = reqUserOptional.get();
        User recUser = recUserOptional.get();

        FriendReq friendReq = new FriendReq(reqUser.getUserId(), recUser.getUserId());

        // 若已存在请求，则修改请求内容
        Optional<FriendReq> friendReqOptional = friendReqRepository
                .findByReqUserIdAndRecUserIdAndFriendReqStatus(reqUser.getUserId(), recUser.getUserId(), FriendReqStatusEnum.REQ);
        if (friendReqOptional.isPresent()) {
            friendReq = friendReqOptional.get();
        }

        friendReq.setReqMsg(StrUtil.blankToDefault(friendReqAddPrivateParam.getReqMsg(), ""));

        friendReqRepository.save(friendReq);

        return RespUtil.OK(buildFriendReqVO(friendReq, reqUser));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespVO<FriendReqVO> updateFriendReqStatus(Long friendReqId, FriendReqStatusEnum friendReqStatus) {
        Optional<FriendReq> friendReqOptional = friendReqRepository.findById(friendReqId);
        if (friendReqOptional.isEmpty()) {
            return RespUtil.error(RespCodeConstant.NOT_FOUND, "好友请求不存在！");
        }

        FriendReq friendReq = friendReqOptional.get();
        friendReq.setFriendReqStatus(friendReqStatus);
        friendReq.setUpdateTime(new Date());
        friendReqRepository.save(friendReq);

        // 同意建立好友关系
        if (friendReqStatus == FriendReqStatusEnum.AGREE) {
            addFriend(friendReq.getReqUserId(), friendReq.getRecUserId());
        }

        return RespUtil.OK(buildFriendReqVO(friendReq));
    }

    /**
     * 建立好友关系
     * @param reqUserId
     * @param recUserId
     */
    private void addFriend(Long reqUserId, Long recUserId) {
        if (!friendRepository.existsByUserIdAndFriendUserId(reqUserId, recUserId)) {
            List<Friend> friends = List.of(new Friend(reqUserId, recUserId), new Friend(recUserId, reqUserId));
            friendRepository.saveAll(friends);
        }
    }

    @Override
    public RespVO<List<FriendReqVO>> getFriendReqs(Long userId) {
        List<FriendReq> reqs = friendReqRepository.findAllByRecUserIdOrderByUpdateTimeDesc(userId);

        List<FriendReqVO> friendReqs = reqs.stream().map(this::buildFriendReqVO).collect(Collectors.toList());

        return RespUtil.OK(friendReqs);
    }

    private FriendReqVO buildFriendReqVO(FriendReq friendReq) {
        User reqUser = userRepository.getOne(friendReq.getReqUserId());
        return buildFriendReqVO(friendReq, reqUser);
    }

    private FriendReqVO buildFriendReqVO(FriendReq friendReq, User reqUser) {
        // 好友请求信息
        FriendReqVO friendReqVO = new FriendReqVO();
        BeanUtil.copyProperties(friendReq, friendReqVO);

        // 请求用户信息
        UserVO reqUserVO = new UserVO();
        BeanUtil.copyProperties(reqUser, reqUserVO);
        friendReqVO.setReqUser(reqUserVO);

        return friendReqVO;
    }

    @Override
    public RespVO deleteFriendReq(Long friendReqId, Long userId) {
        Optional<FriendReq> friendReqOptional = friendReqRepository.findById(friendReqId);
        if (friendReqOptional.isEmpty()) {
            return RespUtil.error(RespCodeConstant.NOT_FOUND, "好友请求不存在！");
        }

        FriendReq friendReq = friendReqOptional.get();
        if (ObjectUtil.notEqual(friendReq.getRecUserId(), userId)) {
            return RespUtil.error(RespCodeConstant.ACCESS_FORBID, "权限不足！");
        }

        friendReqRepository.delete(friendReq);

        return RespUtil.OK();
    }
}
