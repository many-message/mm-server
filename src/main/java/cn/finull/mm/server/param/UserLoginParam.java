package cn.finull.mm.server.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * Description
 * <p> 用户登录参数
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 22:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginParam {

    @NotBlank
    @Length(max = 64)
    private String email;

    @NotBlank
    private String pwd;
}
