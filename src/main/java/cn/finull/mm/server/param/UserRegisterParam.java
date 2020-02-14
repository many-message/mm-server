package cn.finull.mm.server.param;

import cn.finull.mm.server.common.enums.UserSexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Description
 * <p> 用户注册参数
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 22:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterParam {

    @NotBlank
    @Length(max = 32)
    private String nickname;

    @NotBlank
    @Length(max = 64)
    private String email;

    @NotBlank
    @Length(max = 6)
    private String code;

    @NotBlank
    private String pwd;

    @NotNull
    private UserSexEnum sex;
}
