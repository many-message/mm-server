package cn.finull.mm.server.param;

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
 * @date 2020-05-06 18:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendReqAgreeParam {

    /**
     * 请求ID
     */
    @NotNull
    private Long friendReqId;

    /**
     * 分组ID
     */
    @NotNull
    private Long friendGroupId;
}
