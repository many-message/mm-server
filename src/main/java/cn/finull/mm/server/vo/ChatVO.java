package cn.finull.mm.server.vo;

import cn.finull.mm.server.common.enums.ChatTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-06 21:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatVO {

    private Long chatId;

    private Long chatObjId;

    /**
     * 1-私聊；2-群聊
     */
    private ChatTypeEnum chatType;

    private String chatName;

    private String chatTitle;

    private String chatDesc;

    /**
     * 是否有未签收的消息
     */
    private Boolean hasMsg;
}
