package com.insert.ogbsm.presentation.auth;

import com.insert.ogbsm.global.jwt.dto.TokenResponseDto;
import com.insert.ogbsm.presentation.auth.dto.UserLoginDto;
import com.insert.ogbsm.service.auth.AccessTokenRefreshService;
import com.insert.ogbsm.service.auth.UserLoginService;
import com.insert.ogbsm.service.auth.UserLogoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public TokenResponseDto userSignup(@RequestBody @Valid UserLoginDto userLoginDto) throws IOException {
        return ResponseEntity.ok(userLoginService.execute(userLoginDto.getAuthCode())).getBody();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> userLogout(@RequestHeader("refresh_token") String refreshToken) {
        return ResponseEntity.ok(userLogoutService.execute(refreshToken));
    }

    @PutMapping("/refresh/access")
    public TokenResponseDto refreshAccessToken(@RequestHeader("refresh_token") String refreshToken) {
        return ResponseEntity.ok(accessTokenRefreshService.execute(refreshToken)).getBody();
    }
}
