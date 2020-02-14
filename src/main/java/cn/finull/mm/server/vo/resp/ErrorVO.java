package cn.finull.mm.server.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description
 * <p> 错误消息格式
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 21:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorVO {
    private String key;
    private String value;
}
