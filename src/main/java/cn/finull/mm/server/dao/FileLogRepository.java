package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.FileLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-11 15:39
 */
public interface FileLogRepository extends JpaRepository<FileLog, Long> {

    void deleteByCreateTimeBefore(Date createTime);
}
