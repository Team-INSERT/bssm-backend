package com.insert.ogbsm.service.auth.business;

import com.insert.ogbsm.domain.auth.RefreshToken;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.jwt.dto.TokenResponseDto;
import com.insert.ogbsm.presentation.auth.dto.UsingRefreshTokenReq;
import com.insert.ogbsm.service.auth.implement.AuthImplement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthBusiness {
    private final AuthImplement authImplement;

    public TokenResponseDto signUpOrSignIn(String authId) throws IOException {
        User user = authImplement.appendOrUpdateUser(authId);
        authImplement.saveUserSignIn(user.getEmail());
        return authImplement.generateToken(user);
    }

    public void logout(UsingRefreshTokenReq usingRefreshTokenReq) {
        String authId = authImplement.getUserAuthId(usingRefreshTokenReq);
        authImplement.removeUserSignIn(authId);
        authImplement.expireRefreshToken(authId);
    }

    public TokenResponseDto refreshAccessToken(String refreshToken) {
        RefreshToken redisRefreshToken = authImplement.readRefreshToken(refreshToken);
        return authImplement.getNewAccessTokens(redisRefreshToken);
    }
}
