package cn.finull.mm.server.dao;

import cn.finull.mm.server.common.enums.GroupJoinReqStatusEnum;
import cn.finull.mm.server.entity.GroupJoinReq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 23:34
 */
public interface GroupJoinReqRepository extends JpaRepository<GroupJoinReq, Long> {

    Optional<GroupJoinReq> findByGroupIdAndReqUserIdAndAndGroupJoinReqStatus(Long groupId, Long reqUserId, GroupJoinReqStatusEnum groupJoinReqStatus);

    List<GroupJoinReq> findAllByGroupIdInAndGroupJoinReqStatus(List<Long> groupIds, GroupJoinReqStatusEnum groupJoinReqStatus);
}
