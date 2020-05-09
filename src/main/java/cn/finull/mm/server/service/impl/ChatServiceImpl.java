package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.common.enums.ChatTypeEnum;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.common.vo.RespVO;
import cn.finull.mm.server.dao.*;
import cn.finull.mm.server.entity.*;
import cn.finull.mm.server.param.ChatAddParam;
import cn.finull.mm.server.service.ChatService;
import cn.finull.mm.server.service.MsgService;
import cn.finull.mm.server.vo.ChatVO;
import cn.finull.mm.server.vo.MsgVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @date 2020-05-06 21:03
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final FriendRepository friendRepository;

    private final MsgService msgService;

    @Override
    public RespVO<List<ChatVO>> getChats(Long userId) {
        List<ChatVO> chats = chatRepository.findByUserIdOrderByCreateTimeDesc(userId)
                .stream()
                .map(this::buildChatVO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return RespUtil.OK(chats);
    }

    private ChatVO buildChatVO(Chat chat) {
        ChatVO chatVO = new ChatVO();

        // chatId, chatObjId, chatType
        BeanUtil.copyProperties(chat, chatVO);

        chatVO.setHasMsg(Boolean.FALSE);

        if (ChatTypeEnum.USER == chat.getChatType()) {

            Optional<Friend> optional = friendRepository.findByUserIdAndFriendUserId(
                    chat.getUserId(), chat.getChatObjId());
            if (optional.isEmpty()) {
                return null;
            }

            chatVO.setChatTitle(optional.get().getFriendName());

            // 好友信息
            User user = userRepository.getOne(chat.getChatObjId());
            chatVO.setChatName(user.getNickname());
            chatVO.setChatDesc(user.getEmail());

            // 自己的信息
            User me = userRepository.getOne(chat.getUserId());

            // 前10条消息
            List<MsgVO> messages = msgService.getMessages(chat.getChatObjId(), chat.getUserId());
            messages.forEach(message -> {
                if (Objects.equals(chat.getUserId(), message.getSendUserId())) {
                    // 自己
                    message.setFriendName(me.getNickname());
                    message.setNickname(me.getNickname());
                } else {
                    // 好友
                    message.setFriendName(chatVO.getChatTitle());
                    message.setNickname(chatVO.getChatName());
                }
            });
            chatVO.setMessages(messages);

            chatVO.setHasMsg(CollUtil.isNotEmpty(messages));
        } else {

            Optional<Group> optional = groupRepository.findById(chat.getChatObjId());
            if (optional.isEmpty()) {
                return null;
            }
            Group group = optional.get();

            chatVO.setChatName(group.getGroupName());
            chatVO.setChatTitle(group.getGroupName());
            chatVO.setChatDesc(group.getGroupDesc());
        }

        return chatVO;
    }

    @Override
    public RespVO<ChatVO> addChat(ChatAddParam chatAddParam, Long userId) {

        if (ChatTypeEnum.USER == chatAddParam.getChatType()
                && !userRepository.existsById(chatAddParam.getChatObjId())) {
            return RespUtil.error(RespCode.NOT_FOUND);
        } else if (!groupRepository.existsById(chatAddParam.getChatObjId())) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }

        Chat chat = new Chat(userId, chatAddParam.getChatType(), chatAddParam.getChatObjId());
        chatRepository.save(chat);

        return getChat(chat.getChatId());
    }

    private RespVO<ChatVO> getChat(Long chatId) {
        Chat chat = chatRepository.getOne(chatId);
        return RespUtil.OK(buildChatVO(chat));
    }

    @Override
    public RespVO deleteChat(Long chatId, Long userId) {

        if (!chatRepository.existsByChatIdAndUserId(chatId, userId)) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }

        chatRepository.deleteById(chatId);

        return RespUtil.OK();
    }
}
