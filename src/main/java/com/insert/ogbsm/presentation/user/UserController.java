package com.insert.ogbsm.presentation.user;

import com.insert.ogbsm.presentation.user.dto.UserResponseDto;
import com.insert.ogbsm.service.user.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserInfoService userInfoService;

    @GetMapping("/")
    public UserResponseDto findMyInfo() {
        return userInfoService.findMyInfo();
    }

    @GetMapping("/{id}")
    public UserResponseDto findUserInfo(@PathVariable Long id) {
        return userInfoService.findUserInfo(id);
    }
}
