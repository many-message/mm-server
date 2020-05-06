package cn.finull.mm.server.service;

import cn.finull.mm.server.common.vo.RespVO;
import cn.finull.mm.server.param.ChatAddParam;
import cn.finull.mm.server.vo.ChatVO;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-06 21:02
 */
public interface ChatService {

    /**
     * 查询当前聊天列表
     * @param userId
     * @return
     */
    RespVO<List<ChatVO>> getChats(Long userId);

    /**
     * 添加一条聊天消息
     * @param chatAddParam
     * @param userId
     * @return
     */
    RespVO<List<ChatVO>> addChat(ChatAddParam chatAddParam, Long userId);

    /**
     * 删除一条聊天
     * @param chatId
     * @param userId
     * @return
     */
    RespVO<List<ChatVO>> deleteChat(Long chatId, Long userId);
}
