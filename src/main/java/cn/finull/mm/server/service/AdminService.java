package cn.finull.mm.server.service;

import cn.finull.mm.server.param.admin.AdminLoginParam;
import cn.finull.mm.server.param.admin.AdminUpdatePwdParam;
import cn.finull.mm.server.vo.admin.AdminLoginVO;
import cn.finull.mm.server.common.vo.RespVO;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 14:23
 */
public interface AdminService {

    /**
     * 管理员登录
     * @param adminLoginParam
     * @return
     */
    RespVO<AdminLoginVO> login(AdminLoginParam adminLoginParam);

    /**
     * 修改密码
     * @param adminUpdatePwdParam
     * @param adminId
     * @return
     */
    RespVO updatePwd(AdminUpdatePwdParam adminUpdatePwdParam, Long adminId);
}
