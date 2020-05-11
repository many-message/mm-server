package cn.finull.mm.server.controller;

import cn.finull.mm.server.common.vo.RespVO;
import cn.finull.mm.server.service.FileService;
import cn.finull.mm.server.vo.ImageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-11 14:01
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileController {

    private final FileService fileService;

    /**
     * 上传图片
     * @param file
     * @return
     */
    @PostMapping("/images")
    public RespVO<ImageVO> uploadImage(MultipartFile file) {
        return fileService.uploadImage(file);
    }
}
