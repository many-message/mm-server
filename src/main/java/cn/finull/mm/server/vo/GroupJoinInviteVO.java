package cn.finull.mm.server.vo;

import cn.finull.mm.server.common.enums.GroupJoinInviteStatusEnum;
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
    private GroupVO group;
    private UserVO reqUser;
    private UserVO inviteUser;
    private GroupJoinInviteStatusEnum groupJoinInviteStatus;
    private Date createTime;
    private Date updateTime;
}
