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
 * @date 2020-05-06 19:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupJoinReqAddParam {

    @NotNull
    private Long groupId;

    @NotBlank
    @Length(max = 64)
    private String reqMsg;
}
