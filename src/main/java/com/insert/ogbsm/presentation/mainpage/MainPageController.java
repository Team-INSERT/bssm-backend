package com.insert.ogbsm.presentation.mainpage;

import com.insert.ogbsm.domain.post.category.Category;
import com.insert.ogbsm.domain.user.User;
import com.insert.ogbsm.infra.security.util.SecurityUtil;
import com.insert.ogbsm.presentation.bamboo.dto.AllowedBambooRes;
import com.insert.ogbsm.presentation.calender.dto.CalenderReadReq;
import com.insert.ogbsm.presentation.calender.dto.CalenderReadRes;
import com.insert.ogbsm.presentation.calender.dto.CalenderSimpleRes;
import com.insert.ogbsm.presentation.mainpage.dto.MainRes;
import com.insert.ogbsm.presentation.meal.dto.res.MealRes;
import com.insert.ogbsm.presentation.meister.dto.response.MeisterResAndAvgAndMax;
import com.insert.ogbsm.presentation.pagination.Pagination;
import com.insert.ogbsm.presentation.post.dto.PostRes;
import com.insert.ogbsm.service.bamboo.BambooService;
import com.insert.ogbsm.service.calender.CalenderReadService;
import com.insert.ogbsm.service.mainpage.MainPageService;
import com.insert.ogbsm.service.meal.MealService;
import com.insert.ogbsm.service.meister.MeisterRankingService;
import com.insert.ogbsm.service.meister.MeisterService;
import com.insert.ogbsm.service.post.PostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/main")
@RequiredArgsConstructor
public class MainPageController {
    private final MainPageService mainPageService;

    @GetMapping()
    public MainRes get() {
        LocalDate now = LocalDate.now();
        User currentUserOrNotLogin = SecurityUtil.getCurrentUserOrNotLogin();

        return mainPageService.get(now, currentUserOrNotLogin);
    }
}
