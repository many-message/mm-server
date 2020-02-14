package cn.finull.mm.server.entity;

import cn.finull.mm.server.common.enums.UserSexEnum;
import cn.finull.mm.server.common.enums.UserStatusEnum;
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
 * @date 2020-02-13 21:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String pwd;

    @Convert(converter = UserSexEnum.UserSexEnumConverter.class)
    @Column(nullable = false)
    private UserSexEnum sex;

    @Convert(converter = UserStatusEnum.UserStatusEnumConverter.class)
    @Column(name = "user_status", nullable = false)
    private UserStatusEnum userStatus;

    @Column(name = "pwd_err_count", nullable = false)
    private Integer pwdErrCount;

    @Column(name = "create_time", nullable = false)
    private Date createTime;

    @Column(name = "update_time", nullable = false)
    private Date updateTime;
}
