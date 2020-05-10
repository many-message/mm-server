package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.Msg;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Modifying
    @Query("UPDATE #{#entityName} SET sign = 1 WHERE sendUserId = :sendUserId AND recvUserId = :recvUserId AND sign = 0")
    @Transactional
    void signMsgBySendUserIdAndRecvUserId(@Param("sendUserId") Long sendUserId, @Param("recvUserId") Long recvUserId);

    @Query("SELECT m FROM #{#entityName} m WHERE " +
            "((sendUserId = :sendUserId AND recvUserId = :recvUserId) OR " +
            "(sendUserId = :recvUserId AND recvUserId = :sendUserId)) ORDER BY createTime DESC")
    List<Msg> findAllMsg(Pageable pageable, @Param("sendUserId") Long sendUserId, @Param("recvUserId") Long recvUserId);

    @Transactional
    void deleteBySendUserIdAndRecvUserId(Long sendUserId, Long recvUserId);

    @Transactional
    void deleteBySignAndCreateTimeBefore(Boolean sign, Date createTime);

    boolean existsBySendUserIdAndRecvUserIdAndSign(Long sendUserId, Long recvUserId, Boolean sign);
}
