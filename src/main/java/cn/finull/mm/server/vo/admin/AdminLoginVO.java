package cn.finull.mm.server.vo.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 14:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginVO {
    private String token;
    private String username;
}
