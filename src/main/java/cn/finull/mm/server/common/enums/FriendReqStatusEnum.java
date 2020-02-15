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
 * @date 2020-02-13 23:12
 */
@Getter
@AllArgsConstructor
public enum FriendReqStatusEnum implements BaseEnum<FriendReqStatusEnum> {
    /**
     *
     */
    REQ("1", "请求中"),
    AGREE("2", "已同意"),
    IGNORE("3", "已忽略");

    private String code;
    private String desc;

    public static class FriendReqStatusEnumConverter extends JpaEnumConverter<FriendReqStatusEnum> {
        public FriendReqStatusEnumConverter() {
            super(FriendReqStatusEnum.class);
        }
    }
}
