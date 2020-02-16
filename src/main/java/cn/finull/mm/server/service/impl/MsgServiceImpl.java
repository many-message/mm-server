package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.dao.FriendRepository;
import cn.finull.mm.server.dao.MsgRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.Msg;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.param.privates.MsgAddPrivateParam;
import cn.finull.mm.server.service.MsgService;
import cn.finull.mm.server.util.RespUtil;
import cn.finull.mm.server.vo.MsgVO;
import cn.finull.mm.server.vo.UserMsgVO;
import cn.finull.mm.server.vo.UserVO;
import cn.finull.mm.server.vo.resp.RespVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 16:13
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MsgServiceImpl implements MsgService {

    private final MsgRepository msgRepository;
    private final UserRepository userRepository;
    private final FriendRepository friendRepository;

    @Override
    public RespVO addMsg(MsgAddPrivateParam msgAddPrivateParam) {
        Msg msg = new Msg();
        BeanUtil.copyProperties(msgAddPrivateParam, msg);
        msg.setCreateTime(new Date());
        msg.setUpdateTime(new Date());
        msgRepository.save(msg);
        return RespUtil.OK();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespVO<List<UserMsgVO>> getMessages(Long userId) {
        List<Msg> messages = msgRepository.findAllByRecvUserId(userId);
        Set<Long> sendUserIds = messages.parallelStream()
                .filter(msg -> {
                    if (!friendRepository.existsByUserIdAndFriendUserId(msg.getSendUserId(), msg.getRecvUserId())) {
                        msgRepository.delete(msg);
                        return Boolean.FALSE;
                    }
                    return Boolean.TRUE;
                })
                .map(Msg::getSendUserId)
                .collect(Collectors.toSet());
        return RespUtil.OK(buildUserMsgVO(sendUserIds, userId));
    }

    private List<UserMsgVO> buildUserMsgVO(Set<Long> sendUserIds, Long userId) {
        List<UserMsgVO> userMessages = CollUtil.newArrayList();
        sendUserIds.forEach(sendUserId -> {
            List<MsgVO> messages = msgRepository.findAllBySendUserIdAndRecvUserIdOrderByCreateTime(sendUserId, userId)
                    .stream()
                    .map(this::buildMsgVO)
                    .collect(Collectors.toList());
            UserMsgVO userMsgVO = new UserMsgVO(getUserVO(sendUserId), messages);
            userMessages.add(userMsgVO);
        });
        return userMessages;
    }

    private UserVO getUserVO(Long userId) {
        User user = userRepository.getOne(userId);
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }

    private MsgVO buildMsgVO(Msg msg) {
        MsgVO msgVO = new MsgVO();
        BeanUtil.copyProperties(msg, msgVO);
        return msgVO;
    }

    @Override
    public RespVO recvMessage(Long sendUserId, Long recvUserId) {
        msgRepository.deleteBySendUserIdAndRecvUserId(sendUserId, recvUserId);
        return RespUtil.OK();
    }
}
