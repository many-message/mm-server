package cn.finull.mm.server.dao;

import cn.finull.mm.server.common.enums.GroupMemberTypeEnum;
import cn.finull.mm.server.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 23:37
 */
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    long countByGroupId(Long groupId);

    boolean existsByGroupIdAndUserIdAndGroupMemberTypeNot(Long groupId, Long userId, GroupMemberTypeEnum groupMemberType);

    boolean existsByGroupIdAndUserIdAndGroupMemberType(Long groupId, Long userId, GroupMemberTypeEnum groupMemberType);

    void deleteByGroupId(Long groupId);

    List<GroupMember> findAllByGroupIdOrderByGroupMemberType(Long groupId);

    List<GroupMember> findAllByUserId(Long userId);

    boolean existsByGroupIdAndUserId(Long groupId, Long userId);

    List<GroupMember> findAllByGroupIdAndGroupMemberTypeNot(Long groupId, GroupMemberTypeEnum groupMemberType);

    List<GroupMember> findAllByUserIdAndGroupMemberTypeNot(Long userId, GroupMemberTypeEnum groupMemberType);
}
