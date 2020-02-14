package cn.finull.mm.server.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 16:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePwdParam {
    @NotBlank
    private String oldPwd;
    @NotBlank
    private String newPwd;
}
