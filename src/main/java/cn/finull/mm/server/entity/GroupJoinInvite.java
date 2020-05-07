package cn.finull.mm.server.entity;

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
 * @date 2020-02-13 23:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_join_invite")
public class GroupJoinInvite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_join_invite_id")
    private Long groupJoinInviteId;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "req_user_id", nullable = false)
    private Long reqUserId;

    @Column(name = "invite_user_id", nullable = false)
    private Long inviteUserId;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public GroupJoinInvite(Long groupId, Long reqUserId, Long inviteUserId) {
        this.groupId = groupId;
        this.reqUserId = reqUserId;
        this.inviteUserId = inviteUserId;
        createTime = new Date();
        updateTime = new Date();
    }
}
