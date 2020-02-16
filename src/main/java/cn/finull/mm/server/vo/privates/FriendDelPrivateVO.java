package cn.finull.mm.server.vo.privates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 17:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendDelPrivateVO {
    private Long sendUserId;
    private Long recvUserId;
}
