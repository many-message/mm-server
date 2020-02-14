package cn.finull.mm.server.param.admin;

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
 * @date 2020-02-14 14:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginParam {

    @NotBlank
    @Length(max = 32)
    private String username;

    @NotBlank
    private String pwd;
}
