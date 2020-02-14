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
 * @date 2020-02-13 23:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "friend_group")
public class FriendGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_group_id")
    private Long friendGroupId;

    @Column(name = "friend_group_name", nullable = false)
    private String friendGroupName;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public FriendGroup(String friendGroupName, Long userId) {
        this.friendGroupName = friendGroupName;
        this.userId = userId;
        createTime = new Date();
        updateTime = new Date();
    }
}
