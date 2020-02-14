package cn.finull.mm.server.dao;

import cn.finull.mm.server.common.enums.UserStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-14 16:07
 */
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void updatePwdErrCountByUserStatus() {
        userRepository.updatePwdErrCountByUserStatus(0, UserStatusEnum.NORMAL);
    }
}