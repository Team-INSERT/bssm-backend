package com.insert.ogbsm.domain.user.repo;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserWrapper {
    private final UserRepo userRepo;

    public User getUser(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new BsmException(ErrorCode.USER_NOT_FOUND));
    }
}
