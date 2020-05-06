package cn.finull.mm.server.service.impl;

import cn.finull.mm.server.common.config.MmConfig;
import cn.finull.mm.server.common.constant.Constant;
import cn.finull.mm.server.common.constant.RespCode;
import cn.finull.mm.server.common.enums.UserStatusEnum;
import cn.finull.mm.server.dao.FriendGroupRepository;
import cn.finull.mm.server.dao.FriendRepository;
import cn.finull.mm.server.dao.UserRepository;
import cn.finull.mm.server.entity.FriendGroup;
import cn.finull.mm.server.entity.User;
import cn.finull.mm.server.param.UserLoginParam;
import cn.finull.mm.server.param.UserRegisterParam;
import cn.finull.mm.server.param.UserUpdateParam;
import cn.finull.mm.server.param.UserUpdatePwdParam;
import cn.finull.mm.server.service.SecureService;
import cn.finull.mm.server.service.UserService;
import cn.finull.mm.server.util.CacheUtil;
import cn.finull.mm.server.common.util.RespUtil;
import cn.finull.mm.server.vo.UserLoginVO;
import cn.finull.mm.server.vo.UserVO;
import cn.finull.mm.server.vo.admin.UserAdminVO;
import cn.finull.mm.server.common.vo.PageVO;
import cn.finull.mm.server.common.vo.RespVO;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Description
 * <p>
 * Copyright (C) HPE, All rights reserved.
 *
 * @author Ma, Chenxi
 * @date 2020-02-13 22:14
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {

    /**
     * 密码临时锁定次数
     */
    private static final int PWD_TMP_LOCK_COUNT = 5;
    /**
     * 密码永久锁定次数
     */
    private static final int PWD_FOREVER_LOCK_COUNT = 8;

    private final MmConfig mmConfig;
    private final UserRepository userRepository;
    private final FriendGroupRepository friendGroupRepository;
    private final FriendRepository friendRepository;

    private final SecureService secureService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RespVO register(UserRegisterParam userRegisterParam) {
        // 验证邮箱是否被注册
        Optional<User> userOptional = userRepository.findByEmail(userRegisterParam.getEmail());
        if (userOptional.isPresent()) {
            return RespUtil.error(RespCode.BAD_REQUEST, "邮箱已被注册！");
        }

        // 校验验证码
        String code = CacheUtil.getCodeByEmail(userRegisterParam.getEmail());
        if (!StrUtil.equals(code, userRegisterParam.getCode())) {
            return RespUtil.error(RespCode.BAD_REQUEST, "验证码错误！");
        }

        // 校验密码规范
        String pwd = secureService.rsaPrivateKeyDecrypt(userRegisterParam.getPwd());
        if (!secureService.checkPwd(pwd)) {
            return RespUtil.error(RespCode.BAD_REQUEST, "密码长度必须在6~20位！");
        }

        // 保存用户信息
        User user = new User();
        BeanUtil.copyProperties(userRegisterParam, user);
        user.setPwd(secureService.hashByBCrypt(pwd));
        user.setUserStatus(UserStatusEnum.NORMAL);
        user.setPwdErrCount(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        userRepository.save(user);

        // 为用户创建一个默认好友分组
        saveDefaultFriendGroup(user.getUserId());

        return RespUtil.OK();
    }

    private void saveDefaultFriendGroup(Long userId) {
        FriendGroup friendGroup = new FriendGroup();
        friendGroup.setUserId(userId);
        friendGroup.setFriendGroupName(Constant.FRIEND_GROUP_NAME);
        friendGroupRepository.save(friendGroup);
    }

    @Override
    public RespVO<UserLoginVO> login(UserLoginParam userLoginParam) {
        // 验证邮箱
        Optional<User> userOptional = userRepository.findByEmail(userLoginParam.getEmail());
        if (userOptional.isEmpty()) {
            return RespUtil.error(RespCode.BAD_REQUEST, "邮箱错误！");
        }

        // 判断用户是否被锁定
        User user = userOptional.get();
        if (user.getUserStatus() == UserStatusEnum.AUTO_LOCK) {
            return RespUtil.error(RespCode.FORBIDDEN, "密码错误次数过多，已被永久锁定，请联系管理员！");
        }
        if (user.getUserStatus() == UserStatusEnum.ADMIN_LOCK) {
            return RespUtil.error(RespCode.FORBIDDEN, "你已被管理员锁定，暂时不能登录系统！");
        }
        if (user.getPwdErrCount() >= PWD_TMP_LOCK_COUNT) {
            return RespUtil.error(RespCode.FORBIDDEN, "密码错误次数过多，已被临时锁定，请隔天再试！");
        }

        // 校验用户密码
        String pwd = secureService.rsaPrivateKeyDecrypt(userLoginParam.getPwd());
        if (!secureService.checkByBCrypt(pwd, user.getPwd())) {
            user.setPwdErrCount(user.getPwdErrCount() + 1);
            if (user.getPwdErrCount() >= PWD_FOREVER_LOCK_COUNT) {
                user.setUserStatus(UserStatusEnum.AUTO_LOCK);
                userRepository.save(user);
            }
            return RespUtil.error(RespCode.FORBIDDEN, "密码错误！");
        }

        // 清除密码错误次数
        userRepository.updatePwdErrCountByUserStatus(0, UserStatusEnum.NORMAL);

        // 新建token
        String token = IdUtil.simpleUUID();
        CacheUtil.putTokenAndId(Constant.USER_CACHE_PREFIX + token, user.getUserId());

        // 新建返回用户信息
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);

        return RespUtil.OK(new UserLoginVO(token, userVO));
    }

    @Override
    public RespVO<UserVO> updateUserInfo(UserUpdateParam userUpdateParam, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "用户不存在！");
        }

        User user = userOptional.get();
        BeanUtil.copyProperties(userUpdateParam, user, Constant.NOT_COPY_NULL);
        user.setUpdateTime(new Date());
        userRepository.save(user);

        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);

        return RespUtil.OK(userVO);
    }

    @Override
    public RespVO updatePwd(UserUpdatePwdParam userUpdatePwdParam, Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "用户不存在！");
        }

        String oldPwd = secureService.rsaPrivateKeyDecrypt(userUpdatePwdParam.getOldPwd());
        String newPwd = secureService.rsaPrivateKeyDecrypt(userUpdatePwdParam.getNewPwd());

        User user = userOptional.get();
        if (!secureService.checkByBCrypt(oldPwd, user.getPwd())) {
            return RespUtil.error(RespCode.BAD_REQUEST, "原始密码错误！");
        }
        if (!secureService.checkPwd(newPwd)) {
            return RespUtil.error(RespCode.BAD_REQUEST, "密码长度必须在6~20位！");
        }
        user.setPwd(secureService.hashByBCrypt(newPwd));
        user.setUpdateTime(new Date());

        userRepository.save(user);

        return RespUtil.OK();
    }

    @Override
    public RespVO<List<UserVO>> searchUser(String keyword) {
        keyword = "%" + keyword + "%";

        List<UserVO> users = userRepository.findByEmailLikeOrNicknameLike(keyword, keyword)
                .stream()
                .map(this::buildUserVO)
                .collect(Collectors.toList());

        return RespUtil.OK(users);
    }

    private UserVO buildUserVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    public RespVO<UserVO> getUser(Long userId, Long currentUserId) {
        Optional<User> optional = userRepository.findById(userId);

        if (optional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "用户不存在！");
        }

        UserVO userVO = buildUserVO(optional.get());

        Boolean friend = friendRepository.existsByUserIdAndFriendUserId(currentUserId, userId);

        userVO.setFriend(friend);

        return RespUtil.OK(userVO);
    }

    @Override
    public RespVO<PageVO<UserAdminVO>> getUsers(String keyword, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage;
        if (StrUtil.isBlank(keyword)) {
            userPage = userRepository.findAll(pageable);
        } else {
            userPage = userRepository.findAllByEmailOrNicknameLike(keyword, "%" + keyword + "%", pageable);
        }

        PageVO<UserAdminVO> userAdminPage = new PageVO<>(userPage.getTotalElements(),
                userPage.get().map(this::buildUserAdminVO).collect(Collectors.toList()));

        return RespUtil.OK(userAdminPage);
    }

    @Override
    public RespVO<UserAdminVO> updateUserStatus(Long userId, UserStatusEnum userStatus) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "用户不存在！");
        }

        User user = userOptional.get();
        user.setUserStatus(userStatus);
        user.setUpdateTime(new Date());
        userRepository.save(user);

        return RespUtil.OK(buildUserAdminVO(user));
    }

    private UserAdminVO buildUserAdminVO(User user) {
        UserAdminVO userAdminVO = new UserAdminVO();
        BeanUtil.copyProperties(user, userAdminVO);
        return userAdminVO;
    }

    @Override
    public RespVO resetUserPwd(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return RespUtil.error(RespCode.NOT_FOUND, "用户不存在！");
        }

        User user = userOptional.get();
        user.setPwd(secureService.hashByBCrypt(mmConfig.getUserDefaultPwd()));
        user.setUpdateTime(new Date());
        userRepository.save(user);

        return RespUtil.OK();
    }
}
