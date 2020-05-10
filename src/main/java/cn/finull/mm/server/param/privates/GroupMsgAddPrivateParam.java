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
 * @date 2020-05-10 18:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMsgAddPrivateParam {
    @NotNull
    private Long groupId;
    @NotNull
    private Long sendGroupMemberId;
    @NotNull
    private Long sendUserId;
    @NotBlank
    @Length(max = 255)
    private String msgContent;
    @Length(max = 255)
    private String msgAddition;
}
