package cn.finull.mm.server.service;

import cn.finull.mm.server.param.GroupAddParam;
import cn.finull.mm.server.vo.GroupVO;
import cn.finull.mm.server.vo.resp.RespVO;

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
    RespVO<GroupVO> addGroup(GroupAddParam groupAddParam, Long userId);
}
