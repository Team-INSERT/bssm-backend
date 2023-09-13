package com.insert.ogbsm.presentation.auth;

import com.insert.ogbsm.infra.jwt.dto.TokenResponseDto;
import com.insert.ogbsm.presentation.auth.dto.UserLoginReq;
import com.insert.ogbsm.presentation.auth.dto.UsingRefreshTokenReq;
import com.insert.ogbsm.service.auth.AccessTokenRefreshService;
import com.insert.ogbsm.service.auth.UserLoginService;
import com.insert.ogbsm.service.auth.UserLogoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserLoginService userLoginService;
    private final UserLogoutService userLogoutService;
    private final AccessTokenRefreshService accessTokenRefreshService;

    @PostMapping("/oauth/bsm")
    public TokenResponseDto userSignup(@RequestBody @Valid UserLoginReq userLoginReq) throws IOException {
        return ResponseEntity.ok(userLoginService.execute(userLoginReq.getAuthCode())).getBody();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> userLogout(@RequestBody @Valid UsingRefreshTokenReq refreshToken) {
        return ResponseEntity.ok(userLogoutService.execute(refreshToken));
    }

    @PutMapping("/refresh/access")
    public TokenResponseDto refreshAccessToken(@RequestBody @Valid UsingRefreshTokenReq refreshToken) {
        return ResponseEntity.ok(accessTokenRefreshService.execute(refreshToken)).getBody();
    }
}
