package cn.finull.mm.server.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-15 13:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupAddParam {
    @NotBlank
    @Length(max = 32)
    private String groupName;
    @NotBlank
    @Length(max = 64)
    private String groupDesc;
}
