package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.enums.UserStatusEnum;
import cn.finull.mm.server.common.util.DateUtil;
import cn.finull.mm.server.dao.FileLogRepository;
import cn.finull.mm.server.dao.GroupMsgRepository;
import cn.finull.mm.server.dao.MsgRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.service.TaskSchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 16:01
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskSchedulingServiceImpl implements TaskSchedulingService {

    private final UserRepository userRepository;
    private final MsgRepository msgRepository;
    private final GroupMsgRepository groupMsgRepository;
    private final FileLogRepository fileLogRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void cleanUpPwdErrCount() {
        userRepository.updatePwdErrCountByUserStatus(0, UserStatusEnum.NORMAL);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void cleanUpSignMsg() {
        msgRepository.deleteBySignAndCreateTimeBefore(Boolean.TRUE, DateUtil.getBeforeDays(3));
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void cleanUpGroupMsg() {
        groupMsgRepository.deleteByCreateTimeBefore(DateUtil.getBeforeDays(3));
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void cleanUpFiles() {
        fileLogRepository.deleteByCreateTimeBefore(DateUtil.getBeforeDays(7));
    }
}
