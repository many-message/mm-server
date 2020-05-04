package cn.finull.mm.server.entity;

import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 23:34
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_member")
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_member_id")
    private Long groupMemberId;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Convert(converter = GroupMemberTypeEnum.GroupMemberTypeEnumConverter.class)
    @Column(name = "group_member_type", nullable = false)
    private GroupMemberTypeEnum groupMemberType;

    @Column(name = "group_member_name", nullable = false)
    private String groupMemberName;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public GroupMember(Long groupId, Long userId, GroupMemberTypeEnum groupMemberType) {
        this.groupId = groupId;
        this.userId = userId;
        this.groupMemberType = groupMemberType;
        groupMemberName = "";
        createTime = new Date();
        updateTime = new Date();
    }
}
