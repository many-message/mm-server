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
 * @date 2020-02-15 23:15
 */
@Getter
@AllArgsConstructor
public enum GroupStatusEnum implements BaseEnum<GroupStatusEnum> {

    /**
     *
     */
    NORMAL("1", "正常"),
    FORBID("2", "禁用");

    private String code;
    private String desc;

    public static class GroupStatusEnumConverter extends JpaEnumConverter<GroupStatusEnum> {
        public GroupStatusEnumConverter() {
            super(GroupStatusEnum.class);
        }
    }
}
