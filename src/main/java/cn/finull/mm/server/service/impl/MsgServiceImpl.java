package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.Constant;
import cn.finull.mm.server.dao.FriendRepository;
import cn.finull.mm.server.dao.MsgRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.Friend;
import cn.finull.mm.server.entity.Msg;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.param.privates.MsgAddPrivateParam;
import cn.finull.mm.server.service.MsgService;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.vo.MsgVO;
import cn.finull.mm.server.common.vo.RespVO;
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
 * @date 2020-02-16 16:13
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MsgServiceImpl implements MsgService {

    private static final Integer PAGE_SIZE = Constant.PAGE_SIZE;

    private final MsgRepository msgRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Override
    public RespVO addMsg(MsgAddPrivateParam msgAddPrivateParam) {
        Msg msg = new Msg();
        BeanUtil.copyProperties(msgAddPrivateParam, msg);
        msg.setSign(Boolean.FALSE);
        msg.setCreateTime(new Date());
        msg.setUpdateTime(new Date());
        msgRepository.save(msg);
        return RespUtil.OK();
    }

    @Override
    public RespVO<List<MsgVO>> getMessages(Long sendUserId, Integer currentIndex, Long recvUserId) {
        List<MsgVO> messages =  msgRepository.findAllMsg(
                PageRequest.of(currentIndex / PAGE_SIZE, PAGE_SIZE), sendUserId, recvUserId)
                .stream()
                .map(msg -> buildMsgVO(msg, recvUserId))
                .collect(Collectors.toList());

        int index = currentIndex % PAGE_SIZE;
        messages = CollUtil.reverse(CollUtil.sub(messages, index, messages.size()));

        return RespUtil.OK(messages);
    }

    private MsgVO buildMsgVO(Msg msg, Long userId) {
        MsgVO msgVO = new MsgVO();

        BeanUtil.copyProperties(msg, msgVO);

        User sendUser = userRepository.getOne(msg.getSendUserId());

        if (Objects.equals(msg.getSendUserId(), userId)) {
            // 我是发送方
            msgVO.setNickname(sendUser.getNickname());
            msgVO.setFriendName(sendUser.getNickname());
        } else {
            // 我是接收方
            Optional<Friend> optional = friendRepository.findByUserIdAndFriendUserId(
                    msg.getRecvUserId(), msg.getSendUserId());
            if (optional.isPresent()) {
                Friend friend = optional.get();
                msgVO.setFriendName(friend.getFriendName());
                msgVO.setNickname(sendUser.getNickname());
            }
        }

        return msgVO;
    }

    @Override
    public RespVO recvMessage(Long sendUserId, Long recvUserId) {
        msgRepository.signMsgBySendUserIdAndRecvUserId(sendUserId, recvUserId);
        return RespUtil.OK();
    }
}
