package cn.finull.mm.server.controller.privates;

import cn.finull.mm.server.param.privates.GroupMsgAddPrivateParam;
import cn.finull.mm.server.param.privates.MsgAddPrivateParam;
import cn.finull.mm.server.service.GroupMsgService;
import cn.finull.mm.server.service.MsgService;
import cn.finull.mm.server.common.vo.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 16:11
 */
@RestController
@RequestMapping("/private/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MsgPrivateController {

    private final MsgService msgService;
    private final GroupMsgService groupMsgService;

    /**
     * 添加一条消息
     * @param msgAddPrivateParam
     * @return
     */
    @PostMapping("/messages")
    public RespVO addMsg(@Validated @RequestBody MsgAddPrivateParam msgAddPrivateParam) {
        return msgService.addMsg(msgAddPrivateParam);
    }

    /**
     * 添加一条群聊消息
     * @param param
     * @return
     */
    @PostMapping("/groups/messages")
    public RespVO addGroupMsg(@Validated @RequestBody GroupMsgAddPrivateParam param) {
        return groupMsgService.addGroupMsg(param);
    }

}
