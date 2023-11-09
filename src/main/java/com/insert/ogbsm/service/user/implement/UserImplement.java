package com.insert.ogbsm.service.user.implement;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserImplement {
    private final UserRepo userRepo;


    public User readUser(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new BsmException(ErrorCode.USER_NOT_FOUND));
    }
}
