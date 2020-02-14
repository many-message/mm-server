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
 * @date 2020-02-13 21:45
 */
@Getter
@AllArgsConstructor
public enum UserSexEnum implements BaseEnum<UserSexEnum> {
    /**
     *
     */
    MALE("1", "男"),
    FEMALE("2", "女");

    private String code;
    private String desc;

    public static class UserSexEnumConverter extends JpaEnumConverter<UserSexEnum> {
        public UserSexEnumConverter() {
            super(UserSexEnum.class);
        }
    }
}
