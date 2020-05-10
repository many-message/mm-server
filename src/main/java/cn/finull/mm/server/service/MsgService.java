package cn.finull.mm.server.service;

import cn.finull.mm.server.param.privates.MsgAddPrivateParam;
import cn.finull.mm.server.vo.MsgVO;
import cn.finull.mm.server.common.vo.RespVO;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 16:12
 */
public interface MsgService {

    /**
     * 添加一条消息
     * @param msgAddPrivateParam
     * @return
     */
    RespVO addMsg(MsgAddPrivateParam msgAddPrivateParam);

    /**
     * 获取所有未签收的消息
     * @param sendUserId
     * @param currentIndex
     * @param recvUserId
     * @return
     */
    RespVO<List<MsgVO>> getMessages(Long sendUserId, Integer currentIndex, Long recvUserId);

    /**
     * 签收消息
     * @param sendUserId
     * @param recvUserId
     * @return
     */
    RespVO recvMessage(Long sendUserId, Long recvUserId);
}
