package cn.finull.mm.server.service;

import cn.finull.mm.server.param.FriendReqAddParam;
import cn.finull.mm.server.param.FriendReqAgreeParam;
import cn.finull.mm.server.vo.FriendReqVO;
import cn.finull.mm.server.common.vo.RespVO;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 17:00
 */
public interface FriendReqService {

    /**
     * 发送好友请求
     * @param friendReqAddParam
     * @param userId
     * @return
     */
    RespVO sendFriendReq(FriendReqAddParam friendReqAddParam, Long userId);

    /**
     * 同意好友请求
     * @param friendReqAgreeParam
     * @param userId
     * @return
     */
    RespVO<List<FriendReqVO>> agreeFriendReq(FriendReqAgreeParam friendReqAgreeParam, Long userId);

    /**
     * 获取所有好友请求
     * @param userId
     * @return
     */
    RespVO<List<FriendReqVO>> getFriendReqs(Long userId);

    /**
     * 查询好友请求详情
     * @param friendReqId
     * @return
     */
    RespVO<FriendReqVO> getFriendReq(Long friendReqId);

    /**
     * 删除一个好友请求
     * @param friendReqId
     * @param userId
     * @return
     */
    RespVO<List<FriendReqVO>> deleteFriendReq(Long friendReqId, Long userId);
}
