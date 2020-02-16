package cn.finull.mm.server.service;

import cn.finull.mm.server.param.privates.MsgAddPrivateParam;
import cn.finull.mm.server.vo.UserMsgVO;
import cn.finull.mm.server.vo.resp.RespVO;

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
     * @param userId
     * @return
     */
    RespVO<List<UserMsgVO>> getMessages(Long userId);

    /**
     * 签收消息
     * @param sendUserId
     * @param recvUserId
     * @return
     */
    RespVO recvMessage(Long sendUserId, Long recvUserId);
}
