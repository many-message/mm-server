package cn.finull.mm.server.dao;

import cn.finull.mm.server.common.enums.UserStatusEnum;
import cn.finull.mm.server.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 22:14
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE #{#entityName} SET pwdErrCount = :pwdErrCount WHERE userStatus = :userStatus")
    void updatePwdErrCountByUserStatus(@Param("pwdErrCount") Integer pwdErrCount,
                                       @Param("userStatus") UserStatusEnum userStatus);

    List<User> findByEmailLikeOrNicknameLike(String email, String nickname);

    Page<User> findAllByEmailOrNicknameLike(String email, String nickname, Pageable pageable);
}
