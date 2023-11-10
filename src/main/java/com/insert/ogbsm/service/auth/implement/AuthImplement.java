package com.insert.ogbsm.service.auth.implement;

import com.insert.ogbsm.domain.auth.AuthId;
import com.insert.ogbsm.domain.auth.RefreshToken;
import com.insert.ogbsm.domain.auth.repo.AuthIdRepo;
import com.insert.ogbsm.domain.auth.repo.RefreshTokenRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.domain.user.authority.Authority;
import com.insert.ogbsm.domain.user.repo.UserRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.infra.jwt.dto.TokenResponseDto;
import com.insert.ogbsm.infra.jwt.properties.JwtConstants;
import com.insert.ogbsm.infra.jwt.properties.JwtProperties;
import com.insert.ogbsm.infra.jwt.util.JwtProvider;
import com.insert.ogbsm.infra.jwt.util.JwtUtil;
import com.insert.ogbsm.presentation.auth.dto.UsingRefreshTokenReq;
import leehj050211.bsmOauth.BsmOauth;
import leehj050211.bsmOauth.dto.resource.BsmUserResource;
import leehj050211.bsmOauth.exception.BsmOAuthCodeNotFoundException;
import leehj050211.bsmOauth.exception.BsmOAuthInvalidClientException;
import leehj050211.bsmOauth.exception.BsmOAuthTokenNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthImplement {
    private final BsmOauth bsmOauth;
    private final UserRepo userRepo;
    private final AuthIdRepo authIdRepo;
    private final RefreshTokenRepo refreshTokenRepo;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;
    private final JwtUtil jwtUtil;

    public User appendOrUpdateUser(String authId) throws IOException {
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

    public TokenResponseDto generateToken(User user) {
        return jwtProvider.generateToken(user.getEmail(), user.getAuthority().name());
    }

    public void saveUserSignIn(String email) {
        authIdRepo.save(
                AuthId.builder()
                        .id(email)
                        .authId(email)
                        .ttl(jwtProperties.getRefreshExp())
                        .build()
        );
    }

    public String getUserAuthId(UsingRefreshTokenReq usingRefreshTokenReq) {
        return jwtUtil.getJwtBody(
                jwtUtil.replaceBearer(usingRefreshTokenReq.getRefreshToken())
        ).get(JwtConstants.AUTH_ID.message).toString();
    }

    public void removeUserSignIn(String authId) {
        authIdRepo.findByAuthId(authId)
                .ifPresent(authIdRepo::delete);
    }

    public void expireRefreshToken(String authId) {
        refreshTokenRepo.findById(authId)
                .ifPresent(refreshTokenRepo::delete);
    }

    public RefreshToken readRefreshToken(String refreshToken) {
        return refreshTokenRepo.findByRefreshToken(
                jwtUtil.replaceBearer(refreshToken)
        ).orElseThrow(() -> new BsmException(ErrorCode.REFRESH_TOKEN_EXPIRED));
    }

    public TokenResponseDto getNewAccessTokens(final RefreshToken redisRefreshToken) {
        String newAccessToken = jwtProvider.generateAccessToken(redisRefreshToken.getId(), redisRefreshToken.getRole());

        return TokenResponseDto.builder()
                .accessToken(newAccessToken)
                .refreshToken(redisRefreshToken.getRefreshToken())
                .expiredAt(redisRefreshToken.getExpiredAt())
                .build();
    }
}
