package cn.finull.mm.server.service;

import cn.finull.mm.server.vo.FriendGroupVO;
import cn.finull.mm.server.vo.FriendVO;
import cn.finull.mm.server.common.vo.RespVO;

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
     * @return
     */
    List<FriendVO> getFriends(Long friendGroupId);

    /**
     * 查询好友详情
     * @param friendId 好友ID
     * @return
     */
    RespVO<FriendVO> getFriend(Long friendId);

    /**
     * 修改好友备注
     * @param friendId
     * @param friendName
     * @param userId
     * @return
     */
    RespVO<List<FriendGroupVO>> updateFriendName(Long friendId, String friendName, Long userId);

    /**
     * 修改好友分组
     * @param friendId
     * @param friendGroupId
     * @param userId
     * @return
     */
    RespVO<List<FriendGroupVO>> updateFriendGroup(Long friendId, Long friendGroupId, Long userId);

    /**
     * 删除好友
     * @param friendId
     * @param userId
     * @return 被删除用户的id
     */
    RespVO<List<FriendGroupVO>> deleteFriend(Long friendId, Long userId);

    /**
     * 查询我的所有未在指定群聊中的好友
     * @param groupId
     * @param userId
     * @return
     */
    RespVO<List<FriendVO>> getMyFriends(Long groupId, Long userId);
}
