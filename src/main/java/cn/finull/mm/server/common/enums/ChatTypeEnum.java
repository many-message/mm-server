package cn.finull.mm.server.common.enums;

import cn.finull.mm.server.common.config.JpaEnumConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-06 20:59
 */
@Getter
@AllArgsConstructor
public enum ChatTypeEnum implements BaseEnum<ChatTypeEnum> {
    /**
     *
     */
    USER("1", "用户"),
    GROUP("2", "群聊");

    private final String code;
    private final String desc;

    public static class ChatTypeEnumConverter extends JpaEnumConverter<ChatTypeEnum> {
        public ChatTypeEnumConverter() {
            super(ChatTypeEnum.class);
        }
    }
}
