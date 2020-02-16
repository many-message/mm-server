package cn.finull.mm.server.vo.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 15:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO<T> {
    private Long total;
    private List<T> records;
}
