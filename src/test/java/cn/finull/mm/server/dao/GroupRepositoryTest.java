package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.Group;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-04 21:59
 */
@Slf4j
@SpringBootTest
class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Test
    void findAllByGroupIdInOrderByCreateTimeDesc() {
        List<Group> groups = groupRepository.findAllByGroupIdInOrderByCreateTimeDesc(List.of(1L));
        log.info("{}", groups.size());
    }
}