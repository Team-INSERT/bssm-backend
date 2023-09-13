package com.insert.ogbsm.service.auth;

import com.insert.ogbsm.domain.auth.AuthId;
import com.insert.ogbsm.domain.auth.repo.AuthIdRepo;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.jwt.dto.TokenResponseDto;
import com.insert.ogbsm.infra.jwt.properties.JwtProperties;
import com.insert.ogbsm.infra.jwt.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class UserLoginService {
    private final UserSaveService userSaveService;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;
    private final AuthIdRepo authIdRepo;


    public TokenResponseDto execute(String authId) throws IOException {

        User user = userSaveService.execute(authId);
        saveAuthId(user.getEmail());

        return jwtProvider.generateToken(user.getEmail(), user.getAuthority().name());
    }

    private void saveAuthId(String email) {
        authIdRepo.save(
                AuthId.builder()
                        .id(email)
                        .authId(email)
                        .ttl(jwtProperties.getRefreshExp())
                        .build()
        );
    }
}
