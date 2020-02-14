package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 23:21
 */
public interface GroupRepository extends JpaRepository<Group, Long> {
}
