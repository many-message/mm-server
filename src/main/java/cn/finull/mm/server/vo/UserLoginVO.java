package cn.finull.mm.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description
 * <p> 用户登录返回值
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 22:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVO {
    private String token;
    private UserVO userInfo;
}
