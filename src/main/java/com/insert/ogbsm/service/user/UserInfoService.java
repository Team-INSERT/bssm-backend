package com.insert.ogbsm.service.user;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.repo.UserWrapper;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserWrapper userWrapper;

    public UserResponse findMyInfo() {
        return new UserResponse(SecurityUtil.getCurrentUserWithLogin());
    }

    public UserResponse findUserInfo(Long id) {
        User user = userWrapper.getUser(id);
        return new UserResponse(user);
    }
}