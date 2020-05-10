package cn.finull.mm.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-10 18:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMsgVO {

    private Long groupId;

    private Long sendGroupMemberId;
    private Long sendUserId;
    private String nickname;
    private String groupMemberName;

    private String msgContent;

    private String msgAddition;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
