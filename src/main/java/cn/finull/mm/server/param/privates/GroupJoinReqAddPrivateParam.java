package cn.finull.mm.server.param.privates;

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
 * @date 2020-02-16 12:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupJoinReqAddPrivateParam {
    @NotNull
    private Long reqUserId;
    @NotNull
    private Long groupId;
    @NotBlank
    @Length(max = 64)
    private String reqMsg;
}
