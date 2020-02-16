package cn.finull.mm.server.vo.admin;

import cn.finull.mm.server.common.enums.UserSexEnum;
import cn.finull.mm.server.common.enums.UserStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 15:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAdminVO {
    private Long userId;
    private String nickname;
    private String email;
    private UserSexEnum sex;
    private UserStatusEnum userStatus;
    private Integer pwdErrCount;
    private Date createTime;
    private Date updateTime;
}
