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
 * @date 2020-02-13 22:09
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum implements BaseEnum<UserStatusEnum> {
    /**
     *
     */
    NORMAL("1", "正常"),
    AUTO_LOCK("2", "自动锁定"),
    ADMIN_LOCK("3", "管理员锁定");

    private String code;
    private String desc;

    public static class UserStatusEnumConverter extends JpaEnumConverter<UserStatusEnum> {
        public UserStatusEnumConverter() {
            super(UserStatusEnum.class);
        }
    }
}
