package com.insert.ogbsm.presentation.user;

import com.insert.ogbsm.presentation.user.dto.UserResponse;
import com.insert.ogbsm.service.mainpage.MainPageService;
import com.insert.ogbsm.service.user.UserInfoService;
import java.time.LocalDate;
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
    private final MainPageService mainPageService;

    @GetMapping()
    public UserResponse findMyInfo() {
        return userInfoService.findMyInfo();
    }

    @GetMapping("/{id}")
    public UserResponse findUserInfo(@PathVariable Long id) {
        return userInfoService.findUserInfo(id);
    }
}
