package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.config.MmConfig;
import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.common.vo.RespVO;
import cn.finull.mm.server.dao.FileLogRepository;
import cn.finull.mm.server.entity.FileLog;
import cn.finull.mm.server.service.FileService;
import cn.finull.mm.server.vo.ImageVO;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-11 14:02
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileServiceImpl implements FileService {

    private static final String IMAGE = "image";
    private static final int SMALL_IMG_WIDTH = 100;
    private static final int SMALL_IMG_HEIGHT = 100;
    private static final String SMALL_IMG_SUFFIX = ".png";

    private final MmConfig mmConfig;

    private final FileLogRepository fileLogRepository;

    @Override
    public RespVO<ImageVO> uploadImage(MultipartFile file) {
        try {
            if (!StrUtil.startWith(file.getContentType(), IMAGE)) {
                return RespUtil.error(RespCode.FORBIDDEN, "图片格式不规范！");
            }

            String path = mkdirs();

            String filename = buildNewFilename(file.getOriginalFilename());
            file.transferTo(Path.of(path, filename));

            String smallImgName = buildSmallImgName(filename);
            Thumbnails.of(buildPath(path, filename))
                    .size(SMALL_IMG_WIDTH, SMALL_IMG_HEIGHT)
                    .toFile(buildPath(path, smallImgName));

            saveFilename(List.of(filename, smallImgName));

            return RespUtil.OK(new ImageVO(mmConfig.getFileHttpPrefix() + filename,
                    mmConfig.getFileHttpPrefix() + smallImgName));
        } catch (IOException e) {
            log.error("文件上传错误！", e);
            return RespUtil.error(RespCode.INTERNAL_SERVER_ERROR, "文件上传错误！", e);
        }
    }

    /**
     * 如果上传文件的目录不存在，则创建目录
     * 上传文件的目录格式为 file:/xx/xx/，所以需要使用":"进行切分
     * @return 文件路径
     */
    private String mkdirs() {
        String path = StrUtil.split(mmConfig.getFileUploadFolder(), ":")[1];
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file.getPath();
    }

    /**
     * 创建新的文件名，保留原后缀
     * @param filename
     * @return
     */
    private String buildNewFilename(String filename) {
        String suffer = StrUtil.subAfter(filename, ".", true);
        return IdUtil.simpleUUID() + "." + suffer;
    }

    private String buildSmallImgName(String filename) {
        return filename.split("\\.")[0] + "_" + SMALL_IMG_WIDTH + "x" + SMALL_IMG_HEIGHT + SMALL_IMG_SUFFIX;
    }

    private String buildPath(String path, String filename) {
        return path + "/" + filename;
    }

    private void saveFilename(List<String> filenames) {
        List<FileLog> fileLogs = filenames.stream()
                .map(filename -> {
                    FileLog fileLog = new FileLog();
                    fileLog.setFilename(filename);
                    fileLog.setCreateTime(new Date());
                    return fileLog;
                })
                .collect(Collectors.toList());
        fileLogRepository.saveAll(fileLogs);
    }
}
