package cn.finull.mm.server.dao;

import cn.finull.mm.server.entity.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 23:37
 */
public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
}
