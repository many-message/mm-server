package cn.finull.mm.server.vo;

import cn.finull.mm.server.common.enums.GroupJoinReqStatusEnum;
import cn.finull.mm.server.vo.GroupVO;
import cn.finull.mm.server.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 12:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupJoinReqVO {
    private Long groupJoinReqId;
    private UserVO reqUser;
    private GroupVO group;
    private String reqMsg;
    private GroupJoinReqStatusEnum groupJoinReqStatus;
    private Date createTime;
    private Date updateTime;
    private List<Long> recUserIds;
}
