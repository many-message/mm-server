package cn.finull.mm.server.entity;

import cn.finull.mm.server.common.enums.ChatTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-06 20:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private Long chatId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "chat_obj_id", nullable = false)
    private Long chatObjId;

    @Convert(converter = ChatTypeEnum.ChatTypeEnumConverter.class)
    @Column(name = "chat_type", nullable = false)
    private ChatTypeEnum chatType;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public Chat(Long userId, ChatTypeEnum chatType, Long chatObjId) {
        this.userId = userId;
        this.chatType = chatType;
        this.chatObjId = chatObjId;
        createTime = new Date();
        updateTime = new Date();
    }
}
