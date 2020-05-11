package cn.finull.mm.server.service;

import cn.finull.mm.server.common.vo.RespVO;
import cn.finull.mm.server.vo.ImageVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-11 14:02
 */
public interface FileService {

    /**
     * 上传图片
     * @param file
     * @return
     */
    RespVO<ImageVO> uploadImage(MultipartFile file);
}
