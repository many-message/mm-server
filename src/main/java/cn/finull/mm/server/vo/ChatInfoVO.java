package cn.finull.mm.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-10 19:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatInfoVO {

    private Long chatId;

    private List<ChatVO> chats;
}
