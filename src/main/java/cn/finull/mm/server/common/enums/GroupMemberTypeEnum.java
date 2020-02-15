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
 * @date 2020-02-15 12:38
 */
@Getter
@AllArgsConstructor
public enum GroupMemberTypeEnum implements BaseEnum<GroupMemberTypeEnum> {
    /**
     *
     */
    OWNER("1", "群所有者"),
    MANAGER("2", "群管理员"),
    ORDINARY("3", "普通成员");

    private String code;
    private String desc;

    public static class GroupMemberTypeEnumConverter extends JpaEnumConverter<GroupMemberTypeEnum> {
        public GroupMemberTypeEnumConverter() {
            super(GroupMemberTypeEnum.class);
        }
    }
}
