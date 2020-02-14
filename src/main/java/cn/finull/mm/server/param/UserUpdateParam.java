package cn.finull.mm.server.param;

import cn.finull.mm.server.common.enums.UserSexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 16:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateParam {

    @Length(max = 32)
    private String nickname;

    private UserSexEnum sex;
}
