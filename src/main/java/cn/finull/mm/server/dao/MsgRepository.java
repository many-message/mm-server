package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.Msg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-16 16:10
 */
public interface MsgRepository extends JpaRepository<Msg, Long> {

    List<Msg> findAllByRecvUserId(Long recvUserId);

    List<Msg> findAllBySendUserIdAndRecvUserIdOrderByCreateTime(Long sendUserId, Long recvUserId);

    @Transactional
    void deleteBySendUserIdAndRecvUserId(Long sendUserId, Long recvUserId);
}
