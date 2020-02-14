package cn.finull.mm.server.service;

import cn.finull.mm.server.vo.FriendVO;
import cn.finull.mm.server.vo.resp.RespVO;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 20:20
 */
public interface FriendService {

    /**
     * 获取好友信息
     * @param friendGroupId
     * @param userId
     * @return
     */
    RespVO<List<FriendVO>> getFriends(Long friendGroupId, Long userId);

    /**
     * 修改好友备注
     * @param friendId
     * @param friendName
     * @param userId
     * @return
     */
    RespVO<FriendVO> updateFriendName(Long friendId, String friendName, Long userId);

    /**
     * 修改好友分组
     * @param friendId
     * @param friendGroupId
     * @param userId
     * @return
     */
    RespVO updateFriendGroup(Long friendId, Long friendGroupId, Long userId);

    /**
     * 删除好友
     * @param friendId
     * @param userId
     * @return
     */
    RespVO deleteFriend(Long friendId, Long userId);
}
