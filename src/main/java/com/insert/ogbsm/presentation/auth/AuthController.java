package com.insert.ogbsm.presentation.auth;

import com.insert.ogbsm.infra.jwt.dto.TokenResponseDto;
import com.insert.ogbsm.presentation.auth.dto.UserLoginReq;
import com.insert.ogbsm.presentation.auth.dto.UsingRefreshTokenReq;
import com.insert.ogbsm.service.auth.business.AuthBusiness;
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
    private final AuthBusiness authBusiness;

    @PostMapping("/oauth/bsm")
    public TokenResponseDto userSignup(@RequestBody @Valid UserLoginReq userLoginReq) throws IOException {
        return ResponseEntity.ok(authBusiness.signUpOrSignIn(userLoginReq.getAuthCode())).getBody();
    }

    @DeleteMapping("/logout")
    public ResponseEntity<String> userLogout(@RequestBody @Valid UsingRefreshTokenReq refreshToken) {
        authBusiness.logout(refreshToken);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/refresh/access")
    public TokenResponseDto refreshAccessToken(@RequestBody @Valid UsingRefreshTokenReq refreshToken) {
        return ResponseEntity.ok(authBusiness.refreshAccessToken(refreshToken.getRefreshToken())).getBody();
    }
}
