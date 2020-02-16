package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.GroupJoinInvite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 23:28
 */
public interface GroupJoinInviteRepository extends JpaRepository<GroupJoinInvite, Long> {

    List<GroupJoinInvite> findAllByInviteUserIdOrderByUpdateTimeDesc(Long inviteUserId);

    boolean existsByGroupJoinInviteIdAndInviteUserId(Long groupJoinInviteId, Long inviteUserId);
}
