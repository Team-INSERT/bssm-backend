package com.insert.ogbsm.service.auth;

import com.insert.ogbsm.domain.auth.exception.InvalidClientException;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.authority.Authority;
import com.insert.ogbsm.domain.user.exception.UserNotLoginException;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import leehj050211.bsmOauth.BsmOauth;
import leehj050211.bsmOauth.dto.resource.BsmUserResource;
import leehj050211.bsmOauth.exception.BsmOAuthCodeNotFoundException;
import leehj050211.bsmOauth.exception.BsmOAuthInvalidClientException;
import leehj050211.bsmOauth.exception.BsmOAuthTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UserSaveService {

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
        return saveUser(resource);
    }

    @Transactional
    public User saveUser(final BsmUserResource resource) {
        User user = User.builder()
                .id(resource.getUserCode())
                .nickname(resource.getNickname())
                .email(resource.getEmail())
                .profile_image(resource.getProfileUrl())
                .build();

        switch (resource.getRole()) {
            case STUDENT -> user.setStudentValue(resource.getStudent());
            case TEACHER -> user.setTeacherValue(resource.getTeacher());
        }

        return userRepo.save(user);
    }
}
