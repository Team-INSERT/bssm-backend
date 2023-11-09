package com.insert.ogbsm.service.validation;

import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final UserRepo userRepo;

    public void mustBeSameUser(Long id1, Long id2) {
        if (!Objects.equals(id1, id2)) {
            throw new BsmException(ErrorCode.NOT_SAME_USER);
        }
    }

    public void checkUserExist(Long userId) {
        userRepo.findById(userId)
                .orElseThrow(() -> new BsmException(ErrorCode.USER_NOT_FOUND));
    }
}
