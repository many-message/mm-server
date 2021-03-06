package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 23:06
 */
public interface FriendRepository extends JpaRepository<Friend, Long> {

    List<Friend> findByFriendGroupIdOrderByCreateTimeDesc(Long friendGroupId);

    Optional<Friend> findByFriendIdAndUserId(Long friendId, Long userId);

    long countByFriendGroupId(Long friendGroupId);

    void deleteByUserIdAndFriendUserId(Long userId, Long friendUserId);

    boolean existsByUserIdAndFriendUserId(Long userId, Long friendUserId);

    Optional<Friend> findByUserIdAndFriendUserId(Long userId, Long friendUserId);

    List<Friend> findByUserIdAndFriendUserIdNotIn(Long userId, List<Long> friendUserIds);
}
