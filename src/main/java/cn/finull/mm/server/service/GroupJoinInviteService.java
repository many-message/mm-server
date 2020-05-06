package cn.finull.mm.server.service;

import cn.finull.mm.server.param.GroupJoinInviteAddParam;
import cn.finull.mm.server.vo.GroupJoinInviteVO;
import cn.finull.mm.server.common.vo.RespVO;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 13:28
 */
public interface GroupJoinInviteService {

    /**
     * 邀请好友加入群聊
     * @param groupJoinInviteAddParam
     * @param userId
     * @return
     */
    RespVO sendGroupJoinInvite(GroupJoinInviteAddParam groupJoinInviteAddParam, Long userId);

    /**
     * 同意加入群聊
     * @param groupJoinInviteId
     * @param userId
     * @return
     */
    RespVO<List<GroupJoinInviteVO>> agreeGroupJoinInvite(Long groupJoinInviteId, Long userId);

    /**
     * 获取所有入群邀请
     * @param userId
     * @return
     */
    RespVO<List<GroupJoinInviteVO>> getGroupJoinInvites(Long userId);

    /**
     * 删除入群邀请
     * @param groupJoinInviteId
     * @param userId
     * @return
     */
    RespVO<List<GroupJoinInviteVO>> deleteGroupJoinInvite(Long groupJoinInviteId, Long userId);
}
