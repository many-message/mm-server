package cn.finull.mm.server.service;

/**
 * Description
 * <p> 每日任务调度
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 16:00
 */
public interface TaskSchedulingService {

    /**
     * 每日清理密码错误次数
     */
    void cleanUpPwdErrCount();
}
