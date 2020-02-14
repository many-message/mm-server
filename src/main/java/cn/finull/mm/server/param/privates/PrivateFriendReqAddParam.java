package cn.finull.mm.server.param.privates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 16:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateFriendReqAddParam {
    @NotNull
    private Long reqUserId;
    @NotNull
    private Long recUserId;
    @Length(max = 255)
    private String reqMsg;
}
