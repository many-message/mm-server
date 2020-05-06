package cn.finull.mm.server.vo;

import cn.finull.mm.server.common.enums.UserSexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 18:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendReqVO {

    private Long friendReqId;

    /**
     * 请求用户ID
     */
    private Long reqUserId;

    /**
     * 请求用户昵称
     */
    private String nickname;

    /**
     * 请求用户邮箱
     */
    private String email;

    /**
     * 请求用户性别
     */
    private UserSexEnum sex;

    private String reqMsg;

    private Date createTime;

    private Date updateTime;
}
