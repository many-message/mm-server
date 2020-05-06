package cn.finull.mm.server.param;

import cn.finull.mm.server.common.enums.ChatTypeEnum;
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
 * @date 2020-05-06 21:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatAddParam {

    @NotNull
    private Long chatObjId;

    @NotNull
    private ChatTypeEnum chatType;
}
