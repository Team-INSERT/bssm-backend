package com.insert.ogbsm.service.auth;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.authority.Authority;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import leehj050211.bsmOauth.BsmOauth;
import leehj050211.bsmOauth.dto.resource.BsmUserResource;
import leehj050211.bsmOauth.exception.BsmOAuthCodeNotFoundException;
import leehj050211.bsmOauth.exception.BsmOAuthInvalidClientException;
import leehj050211.bsmOauth.exception.BsmOAuthTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserSaveService {

    private final BsmOauth bsmOauth;
    private final UserRepo userRepo;

    public User execute(String authId) throws IOException {
        String token;
        BsmUserResource resource;
        try {
            token = bsmOauth.getToken(authId);
            resource = bsmOauth.getResource(token);
        } catch (BsmOAuthCodeNotFoundException | BsmOAuthTokenNotFoundException e) {
            throw new BsmException(ErrorCode.USER_NOT_LOGIN);
        } catch (BsmOAuthInvalidClientException e) {
            throw new BsmException(ErrorCode.BSM_AUTH_INVALID_CLIENT);
        }
        return checkUserExist(resource);
    }

    public User checkUserExist(final BsmUserResource resource) {
        Optional<User> user = userRepo.findById(resource.getUserCode());

        if (user.isEmpty())
            return SaveNewUser(resource);

        return UpdateUserProfile(user.get(), resource);

    }

    private User SaveNewUser(final BsmUserResource resource) {
        User user = User.builder()
                .id(resource.getUserCode())
                .email(resource.getEmail())
                .nickname(resource.getNickname())
                .authority(Authority.USER)
                .profile_image(resource.getProfileUrl())
                .build();

        switch (resource.getRole()) {
            case STUDENT -> user.setStudentValue(resource.getStudent());
            case TEACHER -> user.setTeacherValue(resource.getTeacher());
        }

        return userRepo.save(user);
    }

    public User UpdateUserProfile(User user, BsmUserResource resource) {
        user.updateUserProfile(resource);
        return user;
    }

}
