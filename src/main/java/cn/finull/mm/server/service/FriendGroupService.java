package cn.finull.mm.server.service;

import cn.finull.mm.server.param.FriendGroupUpdateParam;
import cn.finull.mm.server.vo.FriendGroupPreviewVO;
import cn.finull.mm.server.vo.FriendGroupVO;
import cn.finull.mm.server.common.vo.RespVO;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 19:27
 */
public interface FriendGroupService {

    /**
     * 获取所有分组
     * @param userId
     * @return
     */
    RespVO<List<FriendGroupVO>> getFriendGroups(Long userId);

    /**
     * 修改分组
     * @param friendGroupUpdateParam
     * @param userId
     * @return
     */
    RespVO<List<FriendGroupVO>> updateFriendGroup(FriendGroupUpdateParam friendGroupUpdateParam, Long userId);

    /**
     * 添加好友分组
     * @param friendGroupName
     * @param userId
     * @return
     */
    RespVO<List<FriendGroupVO>> addFriendGroup(String friendGroupName, Long userId);

    /**
     * 删除好友分组
     * @param friendGroupId
     * @param userId
     * @return
     */
    RespVO<List<FriendGroupVO>> deleteFriendGroup(Long friendGroupId, Long userId);

    /**
     * 预览分组列表
     * @param userId
     * @return
     */
    RespVO<List<FriendGroupPreviewVO>> getFriendGroupPreviews(Long userId);
}
