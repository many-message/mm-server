package cn.finull.mm.server.vo;

import cn.finull.mm.server.common.enums.UserSexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 20:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendVO {
    /**
     * 好友ID
     */
    private Long friendId;

    /**
     * 好友备注名
     */
    private String friendName;

    /**
     * 好友用户ID
     */
    private Long userId;

    /**
     * 好友昵称
     */
    private String nickname;

    /**
     * 好友邮箱
     */
    private String email;

    /**
     * 好友性别
     */
    private UserSexEnum sex;

    /**
     * 分组名
     */
    private String friendGroupName;
}
