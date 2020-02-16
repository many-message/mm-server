package cn.finull.mm.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 16:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMsgVO {
    private UserVO sendUser;
    private List<MsgVO> messages;
}
