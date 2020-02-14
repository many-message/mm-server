package cn.finull.mm.server.controller.privates;

import cn.finull.mm.server.common.enums.FriendReqStatusEnum;
import cn.finull.mm.server.param.privates.PrivateFriendReqAddParam;
import cn.finull.mm.server.service.FriendReqService;
import cn.finull.mm.server.vo.FriendReqVO;
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
 * @date 2020-02-14 16:54
 */
@RestController
@RequestMapping("/private/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PrivateFriendReqController {

    private final FriendReqService friendReqService;

    /**
     * 添加一条好友请求记录
     * @param privateFriendReqAddParam
     * @return 请求信息
     */
    @PostMapping("/friend-reqs")
    public RespVO<FriendReqVO> addFriendReq(@Validated @RequestBody PrivateFriendReqAddParam privateFriendReqAddParam) {
        return friendReqService.addFriendReq(privateFriendReqAddParam);
    }

    /**
     * 修改好友请求状态
     * @param friendReqId
     * @param friendReqStatus
     * @return 请求信息
     */
    @PatchMapping("/friend-reqs/{friendReqId}")
    public RespVO<FriendReqVO> updateFriendReqStatus(@PathVariable("friendReqId") Long friendReqId,
                                                     @RequestParam FriendReqStatusEnum friendReqStatus) {
        return friendReqService.updateFriendReqStatus(friendReqId, friendReqStatus);
    }
}
