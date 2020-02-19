package cn.finull.mm.server.controller.privates;

import cn.finull.mm.server.service.GroupMemberService;
import cn.finull.mm.server.vo.resp.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-19 23:43
 */
@RestController
@RequestMapping("/private/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupMemberPrivateController {

    private final GroupMemberService groupMemberService;

    /**
     * 获取一个群所有成员用户id
     * @param groupId
     * @return
     */
    @GetMapping("/{groupId}/group-members")
    public RespVO<List<Long>> getUserIdsByGroupId(@PathVariable("groupId") Long groupId) {
        return groupMemberService.getUserIdsByGroupId(groupId);
    }
}
