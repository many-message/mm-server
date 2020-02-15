package cn.finull.mm.server.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-15 23:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupUpdateParam {
    @NotNull
    private Long groupId;
    @Length(max = 32)
    private String groupName;
    @Length(max = 64)
    private String groupDesc;
}
