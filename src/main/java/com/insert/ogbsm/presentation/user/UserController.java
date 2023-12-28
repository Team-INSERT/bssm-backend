package com.insert.ogbsm.presentation.user;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.user.dto.UserResponse;
import com.insert.ogbsm.service.mainpage.MainPageService;
import com.insert.ogbsm.service.user.business.UserInfoBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserInfoBusiness userInfoBusiness;
    private final MainPageService mainPageService;

    @GetMapping()
    public UserResponse findMyInfo() {
        User user = SecurityUtil.getCurrentUserWithLogin();
        return userInfoBusiness.findMyInfo(user);
    }

    @GetMapping("/{id}")
    public UserResponse findUserInfo(@PathVariable Long id) {
        return userInfoBusiness.findUserInfo(id);
    }
}
