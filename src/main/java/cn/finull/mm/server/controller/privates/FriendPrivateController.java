package cn.finull.mm.server.controller.privates;

import cn.finull.mm.server.service.FriendService;
import cn.finull.mm.server.vo.privates.FriendDelPrivateVO;
import cn.finull.mm.server.common.vo.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 17:02
 */
@RestController
@RequestMapping("/private/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FriendPrivateController {

    private final FriendService friendService;

    /**
     * 删除好友
     * @param userId
     * @param friendId
     * @return
     */
    @DeleteMapping("/{userId}/friends/{friendId}")
    public RespVO<FriendDelPrivateVO> deleteFriend(@PathVariable("userId") Long userId, @PathVariable("friendId") Long friendId) {
        return friendService.deleteFriend(userId, friendId);
    }
}
