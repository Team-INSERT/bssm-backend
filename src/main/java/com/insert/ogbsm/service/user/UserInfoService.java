package com.insert.ogbsm.service.user;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.exception.UserNotFoundException;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.global.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserRepo userRepo;

    public UserResponse findMyInfo() {
        return new UserResponse(SecurityUtil.getCurrentUserWithLogin());
    }

    public UserResponse findUserInfo(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        return new UserResponse(user);
    }
}