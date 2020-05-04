package cn.finull.mm.server.service;

import cn.finull.mm.server.common.enums.GroupStatusEnum;
import cn.finull.mm.server.param.GroupAddParam;
import cn.finull.mm.server.param.GroupUpdateParam;
import cn.finull.mm.server.param.enums.GroupQueryTypeEnum;
import cn.finull.mm.server.vo.GroupVO;
import cn.finull.mm.server.common.vo.PageVO;
import cn.finull.mm.server.common.vo.RespVO;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-15 13:36
 */
public interface GroupService {

    /**
     * 新建一个群
     * @param groupAddParam
     * @param userId
     * @return
     */
    RespVO<List<GroupVO>> addGroup(GroupAddParam groupAddParam, Long userId);

    /**
     * 修改群
     * @param groupUpdateParam
     * @param userId
     * @return
     */
    RespVO<GroupVO> updateGroup(GroupUpdateParam groupUpdateParam, Long userId);

    /**
     * 获取自己的所有群
     * @param type 1-我的群聊；2-我加入的群聊
     * @param userId
     * @return
     */
    RespVO<List<GroupVO>> getGroups(GroupQueryTypeEnum type, Long userId);

    /**
     * 搜索群
     * @param keyword
     * @return
     */
    RespVO<List<GroupVO>> searchGroups(String keyword);

    /**
     * 删除群
     * @param groupId
     * @param userId
     * @return
     */
    RespVO deleteGroup(Long groupId, Long userId);

    // admin

    /**
     * 获取所有群
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    RespVO<PageVO<GroupVO>> getGroups(String keyword, Integer page, Integer size);

    /**
     * 修改群状态
     * @param groupId
     * @param groupStatus
     * @return
     */
    RespVO<GroupVO> updateGroupStatus(Long groupId, GroupStatusEnum groupStatus);
}
