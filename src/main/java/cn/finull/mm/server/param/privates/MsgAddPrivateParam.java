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
 * @date 2020-02-16 16:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgAddPrivateParam {
    @NotNull
    private Long sendUserId;
    @NotNull
    private Long recvUserId;
    @NotBlank
    @Length(max = 255)
    private String msgContent;
    @NotBlank
    @Length(max = 255)
    private String msgAddition;
}
