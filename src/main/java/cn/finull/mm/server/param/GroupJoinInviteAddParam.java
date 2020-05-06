package cn.finull.mm.server.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-06 20:25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupJoinInviteAddParam {

    @NotNull
    private Long groupId;

    @NotNull
    private Long inviteUserId;

    @NotBlank
    @Length(max = 64)
    private String inviteMsg;
}
