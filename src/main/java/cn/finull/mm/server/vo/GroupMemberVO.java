package cn.finull.mm.server.vo;

import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private UserVO groupMemberUser;
    private GroupMemberTypeEnum groupMemberType;
    private String groupMemberName;
}
