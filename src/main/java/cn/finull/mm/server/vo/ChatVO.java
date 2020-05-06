package cn.finull.mm.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-06 21:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatVO {

    private Long chatId;

    /**
     * 图片名字
     */
    private String chatObjName;

    private String chatObjTitle;

    private String chatObjDesc;
}
