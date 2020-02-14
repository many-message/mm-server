package cn.finull.mm.server.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 19:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendGroupUpdateParam {
    @NotNull
    private Long friendGroupId;
    @NotBlank
    @Length(max = 32)
    private String friendGroupName;
}
