package com.insert.ogbsm.service.auth;

import com.insert.ogbsm.domain.auth.repo.AuthIdRepo;
import com.insert.ogbsm.domain.auth.repo.RefreshTokenRepo;
import com.insert.ogbsm.global.jwt.properties.JwtConstants;
import com.insert.ogbsm.global.jwt.util.JwtUtil;
import com.insert.ogbsm.presentation.auth.dto.UsingRefreshTokenReq;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserLogoutService {

    private final JwtUtil jwtUtil;
    private final AuthIdRepo authIdRepo;
    private final RefreshTokenRepo refreshTokenRepo;

    @Transactional
    public String execute(UsingRefreshTokenReq usingRefreshTokenReq) {
        String authId = jwtUtil.getJwtBody(
                jwtUtil.replaceBearer(usingRefreshTokenReq.getRefreshToken())
        ).get(JwtConstants.AUTH_ID.message).toString();

        authIdRepo.findByAuthId(authId)
                .ifPresent(authIdRepo::delete);

        refreshTokenRepo.findById(authId)
                .ifPresent(refreshTokenRepo::delete);

        SecurityContextHolder.clearContext();

        return authId;
    }

}
