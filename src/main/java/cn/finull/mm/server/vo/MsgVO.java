package cn.finull.mm.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 16:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MsgVO {
    private String msgContent;
    private String msgAddition;
    private Date createTime;
}
