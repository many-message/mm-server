package cn.finull.mm.server.param.enums;

import cn.finull.mm.server.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-04 21:15
 */
@Getter
@AllArgsConstructor
public enum GroupQueryTypeEnum implements BaseEnum<GroupQueryTypeEnum> {
    /**
     *
     */
    MY_GROUP("1", "我的群聊"),
    MY_JOIN_GROUP("2", "我加入的群聊");

    private final String code;
    private final String desc;

    @Override
    public String toString() {
        return code;
    }
}
