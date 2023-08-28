package com.insert.ogbsm.global.jwt.util;

import com.insert.ogbsm.domain.auth.RefreshToken;
import com.insert.ogbsm.domain.auth.repo.RefreshTokenRepo;
import com.insert.ogbsm.global.jwt.dto.TokenResponseDto;
import com.insert.ogbsm.global.jwt.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;

import static com.insert.ogbsm.global.jwt.properties.JwtConstants.ACCESS_KEY;
import static com.insert.ogbsm.global.jwt.properties.JwtConstants.AUTH_ID;
import static com.insert.ogbsm.global.jwt.properties.JwtConstants.EMPTY;
import static com.insert.ogbsm.global.jwt.properties.JwtConstants.REFRESH_KEY;
import static com.insert.ogbsm.global.jwt.properties.JwtConstants.ROLE;
import static com.insert.ogbsm.global.jwt.properties.JwtConstants.TYPE;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {
    private final JwtProperties jwtProperties;
    private final RefreshTokenRepo refreshTokenRepo;

    public String generateAccessToken(String authId, String role) {

        return jwtProperties.getPrefix() + EMPTY.getMessage() + generateToken(authId, role, ACCESS_KEY.getMessage(), jwtProperties.getAccessExp());
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
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret()));
        return Jwts.builder()
                .setHeaderParam(TYPE.message, type)
                .claim(ROLE.getMessage(), role)
                .claim(AUTH_ID.getMessage(), authId)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setExpiration(
                        new Date(System.currentTimeMillis() + exp * 1000)
                )
                .compact();
    }

    public ZonedDateTime getExpiredTime() {
        return ZonedDateTime.now().plusSeconds(jwtProperties.getRefreshExp());
    }
}
