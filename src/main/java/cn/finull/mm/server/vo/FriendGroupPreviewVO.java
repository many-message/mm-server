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
 * @date 2020-05-05 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendGroupPreviewVO {

    /**
     * 分组ID
     */
    private Long friendGroupId;

    /**
     * 分组名
     */
    private String friendGroupName;
}
