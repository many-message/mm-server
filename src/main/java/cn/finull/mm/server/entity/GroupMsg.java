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
 * @date 2020-05-10 18:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "group_msg")
public class GroupMsg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_msg_id")
    private Long groupMsgId;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "send_group_member_id", nullable = false)
    private Long sendGroupMemberId;

    @Column(name = "send_user_id", nullable = false)
    private Long sendUserId;

    @Column(name = "msg_content")
    private String msgContent;

    @Column(name = "msg_addition")
    private String msgAddition;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;
}
