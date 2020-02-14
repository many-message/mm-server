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
 * @date 2020-02-14 20:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendVO {
    private Long friendId;
    private String friendName;
    private UserVO friendUser;
}
