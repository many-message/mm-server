package cn.finull.mm.server.param.privates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 13:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupJoinInviteAddPrivateParam {
    @NotNull
    private Long groupId;
    @NotNull
    private Long reqUserId;
    @NotNull
    private Long inviteUserId;
}
