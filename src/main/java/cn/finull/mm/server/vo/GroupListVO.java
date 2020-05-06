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
 * @date 2020-05-06 19:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupListVO {

    private List<GroupVO> myGroups;

    private List<GroupVO> joinGroups;
}
