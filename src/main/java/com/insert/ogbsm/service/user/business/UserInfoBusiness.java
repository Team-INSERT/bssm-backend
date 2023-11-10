package com.insert.ogbsm.service.user.business;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.repo.UserWrapper;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.user.dto.UserResponse;
import com.insert.ogbsm.service.user.implement.UserImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoBusiness {

    private final UserImplement userImplement;

    public UserResponse findMyInfo(User user) {
        return new UserResponse(user);
    }

    public UserResponse findUserInfo(Long id) {
        User user = userImplement.readUser(id);
        return new UserResponse(user);
    }
}