package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.dao.MsgRepository;
import cn.finull.mm.server.entity.Msg;
import cn.finull.mm.server.param.privates.MsgAddPrivateParam;
import cn.finull.mm.server.service.MsgService;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.vo.MsgVO;
import cn.finull.mm.server.common.vo.RespVO;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
    public List<MsgVO> getMessages(Long sendUserId, Long recvUserId) {
        return msgRepository.findAllMsg(sendUserId, recvUserId)
                .stream()
                .map(this::buildMsgVO)
                .collect(Collectors.toList());
    }

    private MsgVO buildMsgVO(Msg msg) {
        MsgVO msgVO = new MsgVO();
        BeanUtil.copyProperties(msg, msgVO);
        return msgVO;
    }

    @Override
    public RespVO recvMessage(Long sendUserId, Long recvUserId) {
        msgRepository.signMsgBySendUserIdAndRecvUserId(sendUserId, recvUserId);
        return RespUtil.OK();
    }
}
