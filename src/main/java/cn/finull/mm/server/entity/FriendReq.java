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
 * @date 2020-02-13 23:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "friend_req")
public class FriendReq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_req_id")
    private Long friendReqId;

    @Column(name = "req_user_id", nullable = false)
    private Long reqUserId;

    @Column(name = "recv_user_id", nullable = false)
    private Long recvUserId;

    @Column(name = "friend_group_id", nullable = false)
    private Long friendGroupId;

    @Column(name = "req_msg", nullable = false)
    private String reqMsg;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public FriendReq(Long reqUserId, Long recvUserId, Long friendGroupId, String reqMsg) {
        this.reqUserId = reqUserId;
        this.recvUserId = recvUserId;
        this.friendGroupId = friendGroupId;
        this.reqMsg = reqMsg;
        createTime = new Date();
        updateTime = new Date();
    }
}
