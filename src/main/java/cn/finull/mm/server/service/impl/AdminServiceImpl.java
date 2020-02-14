package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.constant.Constant;
import cn.finull.mm.server.common.constant.RespCodeConstant;
import cn.finull.mm.server.dao.AdminRepository;
import cn.finull.mm.server.entity.Admin;
import cn.finull.mm.server.param.admin.AdminLoginParam;
import cn.finull.mm.server.param.admin.AdminUpdatePwdParam;
import cn.finull.mm.server.service.AdminService;
import cn.finull.mm.server.service.SecureService;
import cn.finull.mm.server.util.CacheUtil;
import cn.finull.mm.server.util.RespUtil;
import cn.finull.mm.server.vo.admin.AdminLoginVO;
import cn.finull.mm.server.vo.resp.RespVO;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 14:23
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final SecureService secureService;

    @Override
    public RespVO<AdminLoginVO> login(AdminLoginParam adminLoginParam) {
        Optional<Admin> adminOptional = adminRepository.findByUsername(adminLoginParam.getUsername());
        if (adminOptional.isEmpty()) {
            return RespUtil.error(RespCodeConstant.PARAM_INVALID, "用户名错误！");
        }

        String pwd = secureService.rsaPrivateKeyDecrypt(adminLoginParam.getPwd());
        Admin admin = adminOptional.get();
        if (secureService.checkByBCrypt(pwd, admin.getPwd())) {
            String token = IdUtil.simpleUUID();
            CacheUtil.putTokenAndId(Constant.ADMIN_CACHE_PREFIX + token, admin.getAdminId());
            return RespUtil.OK(new AdminLoginVO(token, admin.getUsername()));
        }

        return RespUtil.error(RespCodeConstant.PARAM_INVALID, "密码错误！");
    }

    @Override
    public RespVO updatePwd(AdminUpdatePwdParam adminUpdatePwdParam, Long adminId) {
        Optional<Admin> adminOptional = adminRepository.findById(adminId);
        if (adminOptional.isEmpty()) {
            return RespUtil.error(RespCodeConstant.NOT_FOUND, "用户不存在！");
        }

        String oldPwd = secureService.rsaPrivateKeyDecrypt(adminUpdatePwdParam.getOldPwd());
        String newPwd = secureService.rsaPrivateKeyDecrypt(adminUpdatePwdParam.getNewPwd());

        Admin admin = adminOptional.get();
        if (!secureService.checkByBCrypt(oldPwd, admin.getPwd())) {
            return RespUtil.error(RespCodeConstant.PARAM_INVALID, "原始密码错误！");
        }
        if (!secureService.checkPwd(newPwd)) {
            return RespUtil.error(RespCodeConstant.PARAM_INVALID, "密码长度必须在6~20位！");
        }
        admin.setPwd(secureService.hashByBCrypt(newPwd));
        admin.setUpdateTime(new DateTime());

        adminRepository.save(admin);

        return RespUtil.OK();
    }
}
