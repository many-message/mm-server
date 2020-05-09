package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.common.vo.RespVO;
import cn.finull.mm.server.param.ChatAddParam;
import cn.finull.mm.server.service.ChatService;
import cn.finull.mm.server.vo.ChatVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-06 21:03
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatController {

    private final ChatService chatService;

    /**
     * 查询聊天列表
     * @param userId 当前登录用户ID
     * @return
     */
    @GetMapping("/chats")
    public RespVO<List<ChatVO>> getChats(@RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return chatService.getChats(userId);
    }

    /**
     * 添加一条聊天消息
     * @param chatAddParam
     * @param userId
     * @return
     */
    @PostMapping("/chats")
    public RespVO<ChatVO> addChat(@Validated @RequestBody ChatAddParam chatAddParam,
                                        @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return chatService.addChat(chatAddParam, userId);
    }

    /**
     * 删除一条聊天
     * @param chatId
     * @param userId
     * @return
     */
    @DeleteMapping("/chats/{chatId}")
    public RespVO deleteChat(@PathVariable("chatId") Long chatId,
                                           @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return chatService.deleteChat(chatId, userId);
    }
}
