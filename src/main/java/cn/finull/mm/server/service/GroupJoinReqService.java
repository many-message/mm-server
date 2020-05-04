package cn.finull.mm.server.service;

import cn.finull.mm.server.common.enums.GroupJoinReqStatusEnum;
import cn.finull.mm.server.param.privates.GroupJoinReqAddPrivateParam;
import cn.finull.mm.server.vo.GroupJoinReqVO;
import cn.finull.mm.server.common.vo.RespVO;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 11:22
 */
public interface GroupJoinReqService {

    /**
     * 申请入群
     * @param groupJoinReqAddPrivateParam
     * @return
     */
    RespVO<GroupJoinReqVO> joinGroup(GroupJoinReqAddPrivateParam groupJoinReqAddPrivateParam);

    /**
     * 修改请求状态
     * @param groupJoinReqId
     * @param groupJoinReqStatus
     * @return
     */
    RespVO<GroupJoinReqVO> updateGroupReqStatus(Long groupJoinReqId, GroupJoinReqStatusEnum groupJoinReqStatus);

    /**
     * 获取所有入群请求
     * @param userId
     * @return
     */
    RespVO<List<GroupJoinReqVO>> getGroupJoinReqs(Long userId);
}
