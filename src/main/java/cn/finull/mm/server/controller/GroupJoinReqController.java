package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.service.GroupJoinReqService;
import cn.finull.mm.server.vo.GroupJoinReqVO;
import cn.finull.mm.server.vo.resp.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-15 12:29
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupJoinReqController {

    private final GroupJoinReqService groupJoinReqService;

    /**
     * 获取所有入群请求
     * @param userId
     * @return
     */
    @GetMapping("/group-join-reqs")
    public RespVO<List<GroupJoinReqVO>> getGroupJoinReqs(@RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupJoinReqService.getGroupJoinReqs(userId);
    }
}
