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
import cn.finull.mm.server.vo.ChatInfoVO;
import cn.finull.mm.server.vo.ChatVO;
import cn.finull.mm.server.vo.MsgVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final GroupMemberRepository groupMemberRepository;
    private final MsgRepository msgRepository;

    @Override
    public RespVO<List<ChatVO>> getChats(Long userId) {
        List<ChatVO> chats = chatRepository.findByUserIdOrderByCreateTimeDesc(userId)
                .stream()
                .map(this::buildChatVO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return RespUtil.OK(chats);
    }

    @Override
    public RespVO<ChatVO> getChat(Long chatId) {
        Optional<Chat> optional = chatRepository.findById(chatId);
        if (optional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }
        return RespUtil.OK(buildChatVO(optional.get()));
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

            // 是否有签收消息
            boolean hasMsg = msgRepository.existsBySendUserIdAndRecvUserIdAndSign(
                    chat.getChatObjId(), chat.getUserId(), Boolean.FALSE);
            chatVO.setHasMsg(hasMsg);
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
    public RespVO<ChatInfoVO> addChat(ChatAddParam chatAddParam, Long userId) {

        if (ChatTypeEnum.USER == chatAddParam.getChatType()) {
            if (!friendRepository.existsByUserIdAndFriendUserId(userId, chatAddParam.getChatObjId())) {
                return RespUtil.error(RespCode.FORBIDDEN, "您和他不是好友关系，无法发送消息！");
            }
        } else {
            if (!groupMemberRepository.existsByGroupIdAndUserId(chatAddParam.getChatObjId(), userId)) {
                return RespUtil.error(RespCode.FORBIDDEN, "您不是该群成员，无法发送消息！");
            }
        }

        Long chatId;

        Optional<Chat> optional = chatRepository.findByUserIdAndAndChatObjIdAndAndChatType(
                userId, chatAddParam.getChatObjId(), chatAddParam.getChatType());
        if (optional.isEmpty()) {
            Chat chat = new Chat(userId, chatAddParam.getChatType(), chatAddParam.getChatObjId());
            chatId = chatRepository.save(chat).getChatId();

        } else {
            chatId = optional.get().getChatId();
        }

        ChatInfoVO vo = new ChatInfoVO(chatId, getChats(userId).getData());
        return RespUtil.OK(vo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespVO<List<ChatVO>> deleteChat(Long chatId, Long userId) {

        if (!chatRepository.existsByChatIdAndUserId(chatId, userId)) {
            return RespUtil.error(RespCode.NOT_FOUND);
        }

        chatRepository.deleteById(chatId);

        return getChats(userId);
    }
}
