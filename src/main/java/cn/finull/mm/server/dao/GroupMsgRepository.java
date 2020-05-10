package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.GroupMsg;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-05-10 18:20
 */
public interface GroupMsgRepository extends JpaRepository<GroupMsg, Long> {

    List<GroupMsg> findByGroupIdOrderByCreateTimeDesc(Pageable pageable, Long groupId);

    @Transactional
    void deleteByCreateTimeBefore(Date createTime);

    @Transactional
    void deleteByGroupId(Long groupId);
}
