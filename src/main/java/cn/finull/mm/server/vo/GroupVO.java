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
 * @date 2020-02-15 13:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupVO {
    private Long groupId;
    private String groupName;
    private String groupNum;
    private String groupDesc;
    private Date createTime;
    /**
     * 群成员人数
     */
    private Long groupMemberNum;

    /**
     * 是否已加入
     */
    private Boolean join;
}
