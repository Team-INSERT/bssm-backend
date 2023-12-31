package com.insert.ogbsm.infra.jwt.util;

import com.insert.ogbsm.domain.auth.RefreshToken;
import com.insert.ogbsm.domain.auth.repo.RefreshTokenRepo;
import com.insert.ogbsm.infra.jwt.dto.TokenResponseDto;
import com.insert.ogbsm.infra.jwt.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

import static com.insert.ogbsm.infra.jwt.properties.JwtConstants.*;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepo refreshTokenRepo;

    public String generateAccessToken(String authId, String role) {

        return generateToken(authId, role, ACCESS_KEY.getMessage(), jwtProperties.getAccessExp());
    }

    public TokenResponseDto generateToken(String authId, String role) {

        String accessToken = generateToken(authId, role, ACCESS_KEY.getMessage(), jwtProperties.getAccessExp());
        String refreshToken = generateToken(authId, role, REFRESH_KEY.getMessage(), jwtProperties.getRefreshExp());

        refreshTokenRepo.save(
                RefreshToken.builder()
                        .id(authId)
                        .refreshToken(refreshToken)
                        .ttl(jwtProperties.getRefreshExp())
                        .expiredAt(getExpiredTime())
                        .build()
        );

        return new TokenResponseDto(accessToken, refreshToken, getExpiredTime());
    }


    private String generateToken(String authId, String role, String type, Long exp) {
        return Jwts.builder()
                .setHeaderParam(TYPE.message, type)
                .claim(ROLE.getMessage(), role)
                .claim(AUTH_ID.getMessage(), authId)
                .signWith(jwtProperties.getSecret(), SignatureAlgorithm.HS256)
                .setExpiration(
                        new Date(System.currentTimeMillis() + exp * 1000)
                )
                .compact();
    }

    public ZonedDateTime getExpiredTime() {
        return ZonedDateTime.now().plusSeconds(jwtProperties.getRefreshExp());
    }
}
