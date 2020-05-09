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
 * @date 2020-02-16 16:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "msg")
public class Msg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msg_id")
    private Long msgId;

    @Column(name = "send_user_id", nullable = false)
    private Long sendUserId;

    @Column(name = "recv_user_id", nullable = false)
    private Long recvUserId;

    @Column(name = "msg_content", nullable = false)
    private String msgContent;

    @Column(name = "msg_addition", nullable = false)
    private String msgAddition;

    @Column(name = "sign", columnDefinition = "TINYINT", nullable = false, length = 1)
    private Boolean sign;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;
}
