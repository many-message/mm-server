package cn.finull.mm.server.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    private List<Long> inviteUserIds;
}
