package cn.finull.mm.server.service;

import cn.finull.mm.server.common.enums.GroupJoinInviteStatusEnum;
import cn.finull.mm.server.param.privates.GroupJoinInviteAddPrivateParam;
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
     * 邀请一个好友加入群
     * @param groupJoinInviteAddPrivateParam
     * @return
     */
    RespVO<GroupJoinInviteVO> inviteJoinGroup(GroupJoinInviteAddPrivateParam groupJoinInviteAddPrivateParam);

    /**
     * 修改邀请状态
     * @param groupJoinInviteId
     * @param groupJoinInviteStatus
     * @return
     */
    RespVO<GroupJoinInviteVO> updateGroupInviteStatus(Long groupJoinInviteId, GroupJoinInviteStatusEnum groupJoinInviteStatus);

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
    RespVO deleteGroupJoinInvite(Long groupJoinInviteId, Long userId);
}
