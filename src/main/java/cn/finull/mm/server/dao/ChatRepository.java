package cn.finull.mm.server.dao;

import cn.finull.mm.server.common.enums.ChatTypeEnum;
import cn.finull.mm.server.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-06 21:02
 */
public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findByUserIdOrderByCreateTimeDesc(Long userId);

    boolean existsByChatIdAndUserId(Long chatId, Long userId);

    void deleteByUserIdAndChatObjIdAndChatType(Long userId, Long chatObjId, ChatTypeEnum chatType);

    void deleteByChatObjIdAndChatType(Long chatObjId, ChatTypeEnum chatType);
}
