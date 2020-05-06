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
 * @date 2020-05-06 18:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendReqAddParam {

    /**
     * 接收者ID
     */
    @NotNull
    private Long recvUserId;

    /**
     * 好友所在分组
     */
    @NotNull
    private Long friendGroupId;

    /**
     * 请求消息
     */
    @NotBlank
    @Length(max = 64)
    private String reqMsg;
}
