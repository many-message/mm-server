package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.FriendReq;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 23:17
 */
public interface FriendReqRepository extends JpaRepository<FriendReq, Long> {

    List<FriendReq> findByRecvUserIdOrderByCreateTimeDesc(Long recvUserId);

    Optional<FriendReq> findByFriendReqIdAndRecvUserId(Long friendReqId, Long recvUserId);

    boolean existsByReqUserIdAndRecvUserId(Long reqUserId, Long recvUserId);
}
