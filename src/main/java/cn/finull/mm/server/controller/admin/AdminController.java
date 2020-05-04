package cn.finull.mm.server.controller.admin;

import cn.finull.mm.server.common.constant.RequestConstant;
import cn.finull.mm.server.param.admin.AdminLoginParam;
import cn.finull.mm.server.param.admin.AdminUpdatePwdParam;
import cn.finull.mm.server.service.AdminService;
import cn.finull.mm.server.vo.admin.AdminLoginVO;
import cn.finull.mm.server.common.vo.RespVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 14:18
 */
@RestController
@RequestMapping("/admin/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminController {

    private final AdminService adminService;

    /**
     * 管理员登录
     * @param adminLoginParam
     * @return
     */
    @PostMapping("/login")
    public RespVO<AdminLoginVO> login(AdminLoginParam adminLoginParam) {
        return adminService.login(adminLoginParam);
    }

    /**
     * 修改密码
     * @param adminUpdatePwdParam
     * @param adminId
     * @return
     */
    @PatchMapping("/passwords")
    public RespVO updatePwd(AdminUpdatePwdParam adminUpdatePwdParam,
                            @RequestAttribute(RequestConstant.ADMIN_ID) Long adminId) {
        return adminService.updatePwd(adminUpdatePwdParam, adminId);
    }
}
