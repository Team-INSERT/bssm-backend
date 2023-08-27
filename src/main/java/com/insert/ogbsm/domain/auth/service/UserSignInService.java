package com.insert.ogbsm.domain.auth.service;

import com.insert.ogbsm.domain.auth.exception.InvalidClientException;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.authority.Authority;
import com.insert.ogbsm.domain.user.exception.UserNotLoginException;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import leehj050211.bsmOauth.BsmOauth;
import leehj050211.bsmOauth.dto.resource.*;
import leehj050211.bsmOauth.exception.BsmOAuthCodeNotFoundException;
import leehj050211.bsmOauth.exception.BsmOAuthInvalidClientException;
import leehj050211.bsmOauth.exception.BsmOAuthTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class UserSignInService {

    private final BsmOauth bsmOauth;
    private final UserRepo userRepo;

    @Transactional
    public User execute(String authId) throws IOException {
        String token;
        BsmUserResource resource;
        try {
            token = bsmOauth.getToken(authId);
            resource = bsmOauth.getResource(token);
        } catch (BsmOAuthCodeNotFoundException | BsmOAuthTokenNotFoundException e) {
            throw UserNotLoginException.EXCEPTION;
        } catch (BsmOAuthInvalidClientException e) {
            throw InvalidClientException.EXCEPTION;
        }
        return updateOrSignUp(resource);
    }

    @Transactional
    public User updateOrSignUp(BsmUserResource resource) {
        Optional<User> user = userRepo.findByEmail(resource.getEmail());
        if (user.isEmpty()) {
//            return saveUser(resource);
        }
        User updateUser = user.get();
//        return updateUser.update(resource);
        return updateUser;
    }

    @Transactional
    public void saveUser(final BsmUserResource resource) {
//        return userRepository.save(
//                User.builder()
//                        .email(resource.getEmail())
//                        .nickName(resource.getNickname())
//                        .authority(Authority.USER)
//                        .enroll(resource.getStudent().getEnrolledAt())
//                        .name(resource.getStudent().getName())
//                        .build()
//        );
//    }
    }
}
