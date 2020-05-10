package cn.finull.mm.server.dao;

import cn.finull.mm.server.common.enums.ChatTypeEnum;
import cn.finull.mm.server.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    void deleteByUserIdAndChatObjIdAndChatType(Long userId, Long chatObjId, ChatTypeEnum chatType);

    @Transactional
    void deleteByChatObjIdAndChatType(Long chatObjId, ChatTypeEnum chatType);

    Optional<Chat> findByUserIdAndAndChatObjIdAndAndChatType(Long userId, Long chatObjId, ChatTypeEnum chatType);
}
