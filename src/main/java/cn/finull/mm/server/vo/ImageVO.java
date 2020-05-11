package cn.finull.mm.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-11 14:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageVO {

    /**
     * 图片路径
     */
    private String url;

    /**
     * 缩略图路径
     */
    private String smallUrl;
}
