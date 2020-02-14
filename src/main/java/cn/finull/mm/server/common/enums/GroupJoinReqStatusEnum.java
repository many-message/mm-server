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
 * @date 2020-02-13 23:30
 */
@Getter
@AllArgsConstructor
public enum GroupJoinReqStatusEnum implements BaseEnum<GroupJoinReqStatusEnum> {
    /**
     *
     */
    INVITE("1", "请求中"),
    IGNORE("2", "已忽略");

    private String code;
    private String desc;

    public static class GroupJoinReqStatusEnumConverter extends JpaEnumConverter<GroupJoinReqStatusEnum> {
        public GroupJoinReqStatusEnumConverter() {
            super(GroupJoinReqStatusEnum.class);
        }
    }
}
