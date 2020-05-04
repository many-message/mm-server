package cn.finull.mm.server.service;

import cn.finull.mm.server.common.enums.FriendReqStatusEnum;
import cn.finull.mm.server.param.privates.FriendReqAddPrivateParam;
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
     * 添加好友请求
     * @param friendReqAddPrivateParam
     * @return 请求方用户信息
     */
    RespVO<FriendReqVO> addFriendReq(FriendReqAddPrivateParam friendReqAddPrivateParam);

    /**
     * 修改好友请求状态
     * @param friendReqId
     * @param friendReqStatus
     * @return
     */
    RespVO<FriendReqVO> updateFriendReqStatus(Long friendReqId, FriendReqStatusEnum friendReqStatus);

    /**
     * 获取所有好友请求
     * @param userId
     * @return
     */
    RespVO<List<FriendReqVO>> getFriendReqs(Long userId);

    /**
     * 删除一个好友请求
     * @param friendReqId
     * @param userId
     * @return
     */
    RespVO deleteFriendReq(Long friendReqId, Long userId);
}
