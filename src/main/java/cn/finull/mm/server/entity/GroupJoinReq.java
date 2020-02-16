package cn.finull.mm.server.entity;

import cn.finull.mm.server.common.enums.GroupJoinReqStatusEnum;
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
 * @date 2020-02-13 23:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_join_req")
public class GroupJoinReq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_join_req_id")
    private Long groupJoinReqId;

    @Column(name = "req_user_id", nullable = false)
    private Long reqUserId;

    @Column(name = "group_id", nullable = false)
    private Long groupId;

    @Column(name = "req_msg", nullable = false)
    private String reqMsg;

    @Convert(converter = GroupJoinReqStatusEnum.GroupJoinReqStatusEnumConverter.class)
    @Column(name = "group_join_req_status", nullable = false)
    private GroupJoinReqStatusEnum groupJoinReqStatus;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public GroupJoinReq(Long reqUserId, Long groupId) {
        this.reqUserId = reqUserId;
        this.groupId = groupId;
        reqMsg = "";
        groupJoinReqStatus = GroupJoinReqStatusEnum.REQ;
        createTime = new Date();
        updateTime = new Date();
    }
}
