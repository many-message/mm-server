package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.param.GroupJoinReqAddParam;
import cn.finull.mm.server.service.GroupJoinReqService;
import cn.finull.mm.server.vo.GroupJoinReqVO;
import cn.finull.mm.server.common.vo.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description
 * <p> 申请加入群聊
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
     * 发送加入群聊请求
     * @param groupJoinReqAddParam
     * @param userId
     * @return
     */
    @PostMapping("/group-join-reqs")
    public RespVO<List<Long>> sendGroupJoinReq(@Validated @RequestBody GroupJoinReqAddParam groupJoinReqAddParam,
                                               @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupJoinReqService.sendGroupJoinReq(groupJoinReqAddParam, userId);
    }

    /**
     * 同意入群请求
     * @param groupJoinReqId
     * @param userId
     * @return
     */
    @PatchMapping("/group-join-reqs/{groupJoinReqId}")
    public RespVO<List<GroupJoinReqVO>> agreeGroupJoinReq(@PathVariable("groupJoinReqId") Long groupJoinReqId,
                                                          @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupJoinReqService.agreeGroupJoinReq(groupJoinReqId, userId);
    }

    /**
     * 删除入群请求，只有群所有者才能删除
     * @param groupJoinReqId
     * @param userId
     * @return
     */
    @DeleteMapping("/group-join-reqs/{groupJoinReqId}")
    public RespVO<List<GroupJoinReqVO>> deleteGroupJoinReq(@PathVariable("groupJoinReqId") Long groupJoinReqId,
                                                           @RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupJoinReqService.deleteGroupJoinReq(groupJoinReqId, userId);
    }

    /**
     * 获取所有入群请求
     * @param userId
     * @return
     */
    @GetMapping("/group-join-reqs")
    public RespVO<List<GroupJoinReqVO>> getGroupJoinReqs(@RequestAttribute(RequestConstant.USER_ID) Long userId) {
        return groupJoinReqService.getGroupJoinReqs(userId);
    }

    /**
     * 入群请求详情
     * @param groupJoinReqId
     * @return
     */
    @GetMapping("/group-join-reqs/{groupJoinReqId}")
    public RespVO<GroupJoinReqVO> getGroupJoinReq(@PathVariable("groupJoinReqId") Long groupJoinReqId) {
        return groupJoinReqService.getGroupJoinReq(groupJoinReqId);
    }
}
