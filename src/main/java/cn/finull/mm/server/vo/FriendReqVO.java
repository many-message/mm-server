package cn.finull.mm.server.vo;

import cn.finull.mm.server.common.enums.FriendReqStatusEnum;
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
 * @date 2020-02-14 18:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendReqVO {
    private Long friendReqId;

    private UserVO reqUser;
    private UserVO recUser;

    private String reqMsg;
    private FriendReqStatusEnum friendReqStatus;

    private Date createTime;
    private Date updateTime;
}
