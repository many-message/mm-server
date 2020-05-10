package cn.finull.mm.server.service;

/**
 * Description
 * <p> 任务调度
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

    /**
     * 每日清理已签收的消息
     */
    void cleanUpSignMsg();

    /**
     * 清理群聊消息
     */
    void cleanUpGroupMsg();
}
