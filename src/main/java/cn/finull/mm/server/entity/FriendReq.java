package cn.finull.mm.server.entity;

import cn.finull.mm.server.common.enums.FriendReqStatusEnum;
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

    @Column(name = "rec_user_id", nullable = false)
    private Long recUserId;

    @Column(name = "req_msg", nullable = false)
    private String reqMsg;

    @Convert(converter = FriendReqStatusEnum.FriendReqStatusEnumConverter.class)
    @Column(name = "friend_req_status", nullable = false)
    private FriendReqStatusEnum friendReqStatus;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public FriendReq(Long reqUserId, Long recUserId, String reqMsg) {
        this.reqUserId = reqUserId;
        this.recUserId = recUserId;
        this.reqMsg = reqMsg;
        friendReqStatus = FriendReqStatusEnum.REQ;
        createTime = new Date();
        updateTime = new Date();
    }
}
