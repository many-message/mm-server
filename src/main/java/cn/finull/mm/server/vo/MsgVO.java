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
    /**
     * 发送用户ID
     */
    private Long sendUserId;

    /**
     * 发送用户用户名
     */
    private String nickname;

    /**
     * 发送用户备注
     */
    private String friendName;

    /**
     * 消息正文
     */
    private String msgContent;

    /**
     * 消息附加值
     */
    private String msgAddition;

    /**
     * 发送时间
     */
    private Date createTime;
}
