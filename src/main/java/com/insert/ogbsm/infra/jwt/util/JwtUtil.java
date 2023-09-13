package com.insert.ogbsm.infra.jwt.util;


import com.insert.ogbsm.domain.auth.repo.AuthIdRepo;
import com.insert.ogbsm.infra.error.exception.BsmException;
import com.insert.ogbsm.infra.error.exception.ErrorCode;
import com.insert.ogbsm.infra.jwt.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.insert.ogbsm.infra.jwt.properties.JwtConstants.AUTH_ID;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtProperties jwtProperties;
    private final AuthIdRepo authIdRepo;

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(jwtProperties.getHeader());

        return parseToken(bearer);
    }

    public String replaceBearer(String bearer) {
        if (bearer == null || bearer.isEmpty()) return null;
        return bearer.replaceAll(jwtProperties.getPrefix(), "").trim();
    }

    public String parseToken(String bearer) {
        String token = replaceBearer(bearer);
        if (token != null) {
            checkingIfJwtExpired(token);
            return token;
        }
        return null;
    }

    public void checkingIfJwtExpired(String token) {
        String authId = getJwt(token).getBody().get(AUTH_ID.getMessage()).toString();

        authIdRepo.findByAuthId(authId)
                .orElseThrow(() -> new BsmException(ErrorCode.EXPIRED_JWT));
    }

    public Jws<Claims> getJwt(String token) {
        if (token == null) {
            throw new BsmException(ErrorCode.INVALID_TOKEN);
        }

        return Jwts.parserBuilder().setSigningKey(jwtProperties.getSecret()).build().parseClaimsJws(token);
    }


    public Claims getJwtBody(String bearer) {
        Jws<Claims> jwt = getJwt(parseToken(bearer));
        return jwt.getBody();
    }
}
