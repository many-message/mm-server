package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 23:21
 */
public interface GroupRepository extends JpaRepository<Group, Long> {

    boolean existsByGroupNum(String groupNum);

    List<Group> findAllByGroupIdInOrderByCreateTimeDesc(List<Long> groupIds);

    List<Group> findAllByGroupNumOrGroupNameLike(String groupNum, String groupName);

    Page<Group> findAllByGroupNumOrGroupNameLike(String groupNum, String groupName, Pageable pageable);
}
