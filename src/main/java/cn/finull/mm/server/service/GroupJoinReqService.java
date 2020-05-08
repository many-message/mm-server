package cn.finull.mm.server.service;

import cn.finull.mm.server.param.GroupJoinReqAddParam;
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
     * 申请加入群聊
     * @param groupJoinReqAddParam
     * @param userId
     * @return
     */
    RespVO<List<Long>> sendGroupJoinReq(GroupJoinReqAddParam groupJoinReqAddParam, Long userId);

    /**
     * 同意入群请求
     * @param groupJoinReqId
     * @param userId
     * @return
     */
    RespVO<List<GroupJoinReqVO>> agreeGroupJoinReq(Long groupJoinReqId, Long userId);

    /**
     * 删除入群请求，只有群所有者才能删除
     * @param groupJoinReqId
     * @param userId
     * @return
     */
    RespVO<List<GroupJoinReqVO>> deleteGroupJoinReq(Long groupJoinReqId, Long userId);

    /**
     * 获取所有入群请求
     * @param userId
     * @return
     */
    RespVO<List<GroupJoinReqVO>> getGroupJoinReqs(Long userId);

    /**
     * 查询入群请求详情
     * @param groupJoinReqId
     * @return
     */
    RespVO<GroupJoinReqVO> getGroupJoinReq(Long groupJoinReqId);
}
