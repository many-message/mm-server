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
 * @date 2020-02-16 12:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupJoinReqVO {

    private Long groupJoinReqId;

    /**
     * 请求用户信息
     */
    private Long reqUserId;
    private String nickname;
    private String email;
    private UserSexEnum sex;

    /**
     * 群聊信息
     */
    private Long groupId;
    private String groupName;
    private String groupNum;
    private String groupDesc;

    /**
     * 请求消息
     */
    private String reqMsg;

    private Date createTime;
    private Date updateTime;
}
