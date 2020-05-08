package cn.finull.mm.server.vo;

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
 * @date 2020-02-16 13:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupJoinInviteVO {

    private Long groupJoinInviteId;

    private Long groupId;
    private String groupName;
    private String groupNum;
    private String groupDesc;

    private Long reqUserId;
    private String nickname;
    private String email;

    private Date createTime;
    private Date updateTime;
}
