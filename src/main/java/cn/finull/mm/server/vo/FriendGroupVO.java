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
 * @date 2020-02-14 19:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendGroupVO {

    /**
     * 分组ID
     */
    private Long friendGroupId;

    /**
     * 分组名
     */
    private String friendGroupName;

    /**
     * 好友列表
     */
    private List<FriendVO> friends;
}
