package cn.finull.mm.server.service;

import cn.finull.mm.server.common.vo.RespVO;
import cn.finull.mm.server.param.privates.GroupMsgAddPrivateParam;
import cn.finull.mm.server.vo.GroupMsgVO;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-10 18:20
 */
public interface GroupMsgService {

    /**
     * 添加一条群聊消息
     * @param param
     * @return
     */
    RespVO addGroupMsg(GroupMsgAddPrivateParam param);

    /**
     * 查询群聊消息
     * @param groupId
     * @param currentIndex
     * @return
     */
    RespVO<List<GroupMsgVO>> getGroupMessages(Long groupId, Integer currentIndex);
}
