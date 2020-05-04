package cn.finull.mm.server.controller.privates;

import cn.finull.mm.server.common.enums.GroupJoinReqStatusEnum;
import cn.finull.mm.server.param.privates.GroupJoinReqAddPrivateParam;
import cn.finull.mm.server.service.GroupJoinReqService;
import cn.finull.mm.server.vo.GroupJoinReqVO;
import cn.finull.mm.server.common.vo.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 11:21
 */
@RestController
@RequestMapping("/private/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupJoinReqPrivateController {

    private final GroupJoinReqService groupJoinReqService;

    /**
     * 申请入群
     * @param groupJoinReqAddPrivateParam
     * @return
     */
    @PostMapping("/group-join-reqs")
    public RespVO<GroupJoinReqVO> reqJoinGroup(@Validated @RequestBody GroupJoinReqAddPrivateParam groupJoinReqAddPrivateParam) {
        return groupJoinReqService.joinGroup(groupJoinReqAddPrivateParam);
    }

    /**
     * 修改请求状态
     * @param groupJoinReqId
     * @param groupJoinReqStatus
     * @return
     */
    @PatchMapping("/group-join-reqs/{groupJoinReqId}")
    public RespVO<GroupJoinReqVO> updateGroupReqStatus(@PathVariable("groupJoinReqId") Long groupJoinReqId,
                                                       @RequestParam GroupJoinReqStatusEnum groupJoinReqStatus) {
        return groupJoinReqService.updateGroupReqStatus(groupJoinReqId, groupJoinReqStatus);
    }
}
