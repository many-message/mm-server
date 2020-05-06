package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.service.MsgService;
import cn.finull.mm.server.vo.MsgVO;
import cn.finull.mm.server.common.vo.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 16:12
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MsgController {

    private final MsgService msgService;

    /**
     * 获取所有未签收的消息
     * @param sendUserId
     * @param recvUserId
     * @return
     */
    @GetMapping("/messages/{sendUserId}")
    public RespVO<List<MsgVO>> getMessages(@PathVariable("sendUserId") Long sendUserId,
                                           @RequestAttribute(RequestConstant.USER_ID) Long recvUserId) {
        return msgService.getMessages(sendUserId, recvUserId);
    }

    /**
     * 签收消息
     * @param sendUserId
     * @param recvUserId
     * @return
     */
    @PatchMapping("/messages/{sendUserId}")
    public RespVO recvMessage(@PathVariable("sendUserId") Long sendUserId, @RequestAttribute(RequestConstant.USER_ID) Long recvUserId) {
        return msgService.recvMessage(sendUserId, recvUserId);
    }
}
