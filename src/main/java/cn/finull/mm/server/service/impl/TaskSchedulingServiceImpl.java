package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.enums.UserStatusEnum;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.service.TaskSchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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

    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void cleanUpPwdErrCount() {
        userRepository.updatePwdErrCountByUserStatus(0, UserStatusEnum.NORMAL);
    }
}
