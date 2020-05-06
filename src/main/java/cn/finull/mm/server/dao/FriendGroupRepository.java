package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.FriendGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 23:11
 */
public interface FriendGroupRepository extends JpaRepository<FriendGroup, Long> {

    List<FriendGroup> findAllByUserIdOrderByCreateTimeAsc(Long userId);

    Optional<FriendGroup> findByFriendGroupIdAndUserId(Long friendGroupId, Long userId);

    boolean existsByUserIdAndFriendGroupName(Long userId, String friendGroupName);

    int countByUserId(Long userId);

    Optional<FriendGroup> findByUserIdAndFriendGroupName(Long userId, String friendGroupName);
}
