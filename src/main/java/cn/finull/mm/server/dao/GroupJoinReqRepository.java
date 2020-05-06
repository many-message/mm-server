package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.GroupJoinReq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 23:34
 */
public interface GroupJoinReqRepository extends JpaRepository<GroupJoinReq, Long> {

    void deleteByGroupId(Long groupId);

    boolean existsByReqUserIdAndGroupId(Long reqUser, Long groupId);

    List<GroupJoinReq> findByGroupIdInOrderByCreateTimeDesc(List<Long> groupIds);
}
