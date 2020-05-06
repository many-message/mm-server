package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.common.enums.ChatTypeEnum;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.common.vo.RespVO;
import cn.finull.mm.server.dao.ChatRepository;
import cn.finull.mm.server.dao.FriendRepository;
import cn.finull.mm.server.dao.GroupRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.Chat;
import cn.finull.mm.server.entity.Friend;
import cn.finull.mm.server.entity.Group;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.param.ChatAddParam;
import cn.finull.mm.server.service.ChatService;
import cn.finull.mm.server.vo.ChatVO;
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
        chatVO.setChatId(chat.getChatId());

        if (ChatTypeEnum.USER == chat.getChatType()) {
            Optional<Friend> optional = friendRepository.findByUserIdAndFriendUserId(
                    chat.getUserId(), chat.getChatObjId());

            if (optional.isEmpty()) {
                return null;
            }

            chatVO.setChatObjName(optional.get().getFriendName());

            User user = userRepository.getOne(chat.getChatObjId());
            chatVO.setChatObjTitle(user.getNickname());
            chatVO.setChatObjDesc(user.getEmail());
        } else {
            Group group = groupRepository.getOne(chat.getChatObjId());
            chatVO.setChatObjTitle(group.getGroupName());
            chatVO.setChatObjTitle(group.getGroupName());
            chatVO.setChatObjDesc(group.getGroupDesc());
        }

        return chatVO;
    }

    @Override
    public RespVO<List<ChatVO>> addChat(ChatAddParam chatAddParam, Long userId) {

        if (ChatTypeEnum.USER == chatAddParam.getChatType()
                && !userRepository.existsById(chatAddParam.getChatObjId())) {
            return RespUtil.error(RespCode.NOT_FOUND);
        } else if (!groupRepository.existsById(chatAddParam.getChatObjId())) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }

        Chat chat = new Chat(userId, chatAddParam.getChatType(), chatAddParam.getChatObjId());
        chatRepository.save(chat);

        return getChats(userId);
    }

    @Override
    public RespVO<List<ChatVO>> deleteChat(Long chatId, Long userId) {

        if (!chatRepository.existsByChatIdAndUserId(chatId, userId)) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }

        chatRepository.deleteById(chatId);

        return getChats(userId);
    }
}
