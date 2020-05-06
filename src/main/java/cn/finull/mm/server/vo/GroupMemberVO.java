package cn.finull.mm.server.vo;

import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
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
 * @date 2020-02-15 23:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMemberVO {

    private Long groupMemberId;

    /**
     * 1-群所有者；2-群管理员；3-普通成员
     */
    private GroupMemberTypeEnum groupMemberType;

    private String groupMemberName;

    private Long userId;

    private String nickname;

    private String email;
}
