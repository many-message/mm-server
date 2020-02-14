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
 * @date 2020-02-13 23:24
 */
@Getter
@AllArgsConstructor
public enum GroupJoinInviteStatusEnum implements BaseEnum<GroupJoinInviteStatusEnum> {

    /**
     *
     */
    INVITE("1", "邀请中"),
    IGNORE("2", "已忽略");

    private String code;
    private String desc;

    public static class GroupJoinInviteStatusEnumConverter extends JpaEnumConverter<GroupJoinInviteStatusEnum> {
        public GroupJoinInviteStatusEnumConverter() {
            super(GroupJoinInviteStatusEnum.class);
        }
    }
}
