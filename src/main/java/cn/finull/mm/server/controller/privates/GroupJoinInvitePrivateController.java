package cn.finull.mm.server.controller.privates;

import cn.finull.mm.server.common.enums.GroupJoinInviteStatusEnum;
import cn.finull.mm.server.param.privates.GroupJoinInviteAddPrivateParam;
import cn.finull.mm.server.service.GroupJoinInviteService;
import cn.finull.mm.server.vo.GroupJoinInviteVO;
import cn.finull.mm.server.vo.resp.RespVO;
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
 * @date 2020-02-16 13:27
 */
@RestController
@RequestMapping("/private/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupJoinInvitePrivateController {

    private final GroupJoinInviteService groupJoinInviteService;

    /**
     * 邀请一个好友加入群
     * @param groupJoinInviteAddPrivateParam
     * @return
     */
    @PostMapping("/group-join-invites")
    public RespVO<GroupJoinInviteVO> inviteJoinGroup(@Validated @RequestBody GroupJoinInviteAddPrivateParam groupJoinInviteAddPrivateParam) {
        return groupJoinInviteService.inviteJoinGroup(groupJoinInviteAddPrivateParam);
    }

    /**
     * 修改邀请状态
     * @param groupJoinInviteId
     * @param groupJoinInviteStatus
     * @return
     */
    @PatchMapping("/group-join-invites/{groupJoinInviteId}")
    public RespVO<GroupJoinInviteVO> updateGroupInviteStatus(@PathVariable("groupJoinInviteId") Long groupJoinInviteId,
                                                             @RequestParam GroupJoinInviteStatusEnum groupJoinInviteStatus) {
        return groupJoinInviteService.updateGroupInviteStatus(groupJoinInviteId, groupJoinInviteStatus);
    }
}
