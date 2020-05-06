package cn.finull.mm.server.entity;

import cn.finull.mm.server.common.enums.GroupStatusEnum;
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
 * @date 2020-02-13 23:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "group_chat")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "group_num", nullable = false, unique = true)
    private String groupNum;

    @Column(name = "group_desc", nullable = false)
    private String groupDesc;

    @Convert(converter = GroupStatusEnum.GroupStatusEnumConverter.class)
    @Column(name = "group_status", nullable = false)
    private GroupStatusEnum groupStatus;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;

    public Group(String groupName, String groupDesc, String groupNum) {
        this.groupName = groupName;
        this.groupDesc = groupDesc;
        this.groupNum = groupNum;
        groupStatus = GroupStatusEnum.NORMAL;
        createTime = new Date();
        updateTime = new Date();
    }
}
