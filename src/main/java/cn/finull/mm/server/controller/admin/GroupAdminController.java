package cn.finull.mm.server.controller.admin;

import cn.finull.mm.server.common.enums.GroupStatusEnum;
import cn.finull.mm.server.service.GroupService;
import cn.finull.mm.server.vo.GroupVO;
import cn.finull.mm.server.vo.resp.PageVO;
import cn.finull.mm.server.vo.resp.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 15:12
 */
@RestController
@RequestMapping("/admin/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupAdminController {

    private final GroupService groupService;

    /**
     * 获取所有群
     * @param keyword
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/groups")
    public RespVO<PageVO<GroupVO>> getGroups(@RequestParam(required = false) String keyword,
                                             @RequestParam(required = false, defaultValue = "1") Integer page,
                                             @RequestParam(required = false, defaultValue = "10") Integer size) {
        return groupService.getGroups(keyword, page, size);
    }

    /**
     * 修改群状态
     * @param groupId
     * @param groupStatus
     * @return
     */
    @PatchMapping("/groups/{groupId}")
    public RespVO updateGroupStatus(@PathVariable("groupId") Long groupId,
                                    @RequestParam GroupStatusEnum groupStatus) {
        return groupService.updateGroupStatus(groupId, groupStatus);
    }
}
