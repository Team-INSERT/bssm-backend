package com.insert.ogbsm.presentation.mainpage;

import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.mainpage.dto.MainRes;
import com.insert.ogbsm.presentation.mainpage.dto.dto.AsideRes;
import com.insert.ogbsm.service.mainpage.AsideService;
import com.insert.ogbsm.service.mainpage.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainPageController {
    private final MainPageService mainPageService;
    private final AsideService asideService;

    @GetMapping()
    public MainRes get() {
        LocalDate now = LocalDate.now();
        User currentUserOrNotLogin = SecurityUtil.getCurrentUserWithLogin();

        return mainPageService.get(now, currentUserOrNotLogin);
    }

    @GetMapping("/aside")
    public AsideRes asideGet() {
        LocalDate now = LocalDate.now();
        User currentUserOrNotLogin = SecurityUtil.getCurrentUserWithLogin();

        return asideService.get(now, currentUserOrNotLogin);
    }
}
