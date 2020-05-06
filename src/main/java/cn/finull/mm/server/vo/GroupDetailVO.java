package cn.finull.mm.server.vo;

import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import cn.finull.mm.server.common.enums.GroupStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-06 12:31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetailVO {

    private Long groupId;

    private String groupName;

    private String groupNum;

    private String groupDesc;

    private Date createTime;

    private GroupStatusEnum groupStatus;

    /**
     * 群主
     */
    private String groupOwnerName;

    /**
     * 我在群聊中的类型
     */
    private GroupMemberTypeEnum myGroupMemberType;

    private Long myUserId;

    /**
     * 群成员
     */
    private List<GroupMemberVO> groupMembers;
}
